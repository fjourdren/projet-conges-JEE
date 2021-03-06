<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff.Dayoff" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff_type.DayoffType" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%
    List<Dayoff> dayoffs = (List<Dayoff>) request.getAttribute("dayoffs");
    List<DayoffType> dayoffTypes = (List<DayoffType>) request.getAttribute("dayoffTypes");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+00:00'");
%>

<style>
    #calendar {
        max-width: 900px;
        margin: 40px auto;
    }
</style>

<!-- Calendar (dynamic) -->
<div id="calendar"></div>

<!-- Edit Modal -->
<div id="edit-modal" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <form style="width: 100%; height: 100%;" action="" method="post" id="edit-form">
            <input type="hidden" name="dayoff-id" id="dayoff-id-input" value>
            <input type="hidden" name="dayoff-action" id="dayoff-action-input" value="edit">
            <div class="modal-content">
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="start-date-input">Date de début</label>
                            <div class="input-group mb-2">
                                <input type="date" class="form-control" name="start-date" id="start-date-input"
                                       required>
                                <span class="input-group-text" id="start-date-input-addon">
                                    <select name="start-date-type" id="start-date-type-id">
                                        <option value="MORNING">Matin</option>
                                        <option value="AFTERNOON">Après-midi</option>
                                    </select>
                                </span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="end-date-input">Date de fin</label>
                            <div class="input-group mb-2">
                                <input type="date" class="form-control" name="end-date" id="end-date-input" required>
                                <span class="input-group-text" id="end-date-input-addon">
                                    <select name="end-date-type" id="end-date-type-id">
                                        <option value="MORNING">Matin</option>
                                        <option value="AFTERNOON">Après-midi</option>
                                    </select>
                                </span>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="employee-comment-input" class="col-sm-2 col-form-label">Motif</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="employee-comment"
                                       id="employee-comment-input" placeholder="Motif">
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="dayoff-type-select" class="col-sm-2 col-form-label">Type</label>
                            <div class="col-sm-10">
                                <select name="dayoff-type" id="dayoff-type-select">
                                    <% for (DayoffType type : dayoffTypes) { %>
                                    <option value="<%= type.getId() %>"><%= type.getName() %>
                                    </option>
                                    <% } %>
                                </select>
                            </div>
                        </div>

                        <br>
                        Etat : <span id="state-div"></span>
                        <br>
                        Commentaire RH : <span id="rh-comment-div"></span>
                    </form>
                </div>
                <div class="modal-footer">
                    <button onclick="onDeleteButtonClick()" id="delete-button" type="button"
                            class="mr-auto btn btn-danger">Supprimer
                    </button>
                    <button onclick="onCancelButtonClick()" type="button" class="btn btn-secondary">Annuler</button>
                    <input type="submit" value="Valider" class="btn btn-primary" id="submit-button">
                </div>
            </div>
        </form>
    </div>
</div>

<!-- JS source data (dynamic) -->
<script>
    <% DateFormat simpleDayFormat = new SimpleDateFormat("yyyy-MM-dd"); %>
    const events = [
        <% for (Dayoff dayoff : dayoffs) { %>
        <%
        StringBuilder titleBuilder = new StringBuilder();
        titleBuilder.append("Du ");
        titleBuilder.append(simpleDayFormat.format(dayoff.getDateStart()));
        titleBuilder.append(dayoff.getDateStart().getHours() == 12 ? " après-midi" : " matin" );
        titleBuilder.append(" au ");
        titleBuilder.append(simpleDayFormat.format(dayoff.getDateEnd()));
        titleBuilder.append(dayoff.getDateEnd().getHours() == 12 ? " après-midi" : " matin" );

        String eventColor = "blue";
        switch (dayoff.getStatus()){
            case WAITING:{
                eventColor = "lightgray";
                break;
            }
            case ACCEPTED:{
                eventColor = "lightgreen";
                break;
            }
            case REFUSED:{
                eventColor = "lightcoral";
                break;
            }
        }

        %>

        {
            id: "<%= dayoff.getId() %>",
            title: "<%= titleBuilder.toString() %>",
            start: "<%= dateFormat.format(dayoff.getDateStart()) %>",
            end: "<%= dateFormat.format(dayoff.getDateEnd()) %>",
            rhComment: "<%= (dayoff.getCommentRH() == null) ? "<aucun>" : dayoff.getCommentRH()%>",
            employeeComment: "<%= (dayoff.getCommentEmployee() == null) ? "" : dayoff.getCommentEmployee()%>",
            typeId: "<%= dayoff.getType().getId() %>",
            state: "<%= dayoff.getStatus().toString() %>",
            color: "<%= eventColor %>",
        },
        <% } %>
    ];
</script>

<!-- JS logic -->
<script>
    let calendar = undefined;
    const EDIT_MODAL = $("#edit-modal");
    const UNSAVED_DAYOFF_ID = "unsaved";

    // Appellé après que l'utilisateur a sélectionné une période, pour pouvoir ajouter une nouvelle demande de congés
    function onDateRangeSelection(data) {
        console.log("onDateRangeSelection", data);
        calendar.unselect();
        calendar.addEvent({id: UNSAVED_DAYOFF_ID, title: "", start: data.start, end: data.end, allDay: true});

        setEditFormReadOnly(false);
        document.getElementById("dayoff-id-input").value = "";
        document.getElementById("start-date-input").value = data.startStr;
        document.getElementById("start-date-type-id").value = "MORNING";
        document.getElementById("end-date-input").value = data.endStr;
        document.getElementById("end-date-type-id").value = "MORNING";
        document.getElementById("employee-comment-input").value = "";
        document.getElementById("dayoff-type-select").value = <%= dayoffTypes.get(0).getId() %>;
        document.getElementById("state-div").textContent = "En cours de création";
        document.getElementById("delete-button").setAttribute('disabled', "");
        document.getElementById("rh-comment-div").textContent = "<aucun>";

        EDIT_MODAL.modal({keyboard: false, backdrop: 'static'});
    }

    // Appellé après que l'utilisateur a cliqué sur un élément du calendrier
    // pour voir les informations sur la demande de congés et la modifier si possible
    function onCalendarItemClick(eventObject) {
        const dayoff = eventObject.event;
        console.log("onCalendarItemClick", eventObject);
        setEditFormReadOnly(false);

        //les dates en JS sont en GMT+1
        document.getElementById("dayoff-id-input").value = dayoff.id;
        document.getElementById("start-date-input").value = dayoff.start.toISOString().split('T')[0];
        document.getElementById("start-date-type-id").value = (dayoff.start.getHours() === 13) ? "AFTERNOON" : "MORNING";
        document.getElementById("end-date-input").value = dayoff.end.toISOString().split('T')[0];
        document.getElementById("end-date-type-id").value = (dayoff.end.getHours() === 13) ? "AFTERNOON" : "MORNING";
        document.getElementById("employee-comment-input").value = dayoff.extendedProps.employeeComment;
        document.getElementById("dayoff-type-select").value = dayoff.extendedProps.typeId;
        document.getElementById("rh-comment-div").textContent = dayoff.extendedProps.rhComment;

        switch (dayoff.extendedProps.state) {
            case "WAITING":
                document.getElementById("state-div").textContent = "En attente de validation";
                setEditFormReadOnly(false);
                break;
            case "ACCEPTED":
                document.getElementById("state-div").textContent = "Accepté";
                setEditFormReadOnly(true);
                break;
            case "REFUSED":
                document.getElementById("state-div").textContent = "Refusé";
                setEditFormReadOnly(true);
                break;
        }

        EDIT_MODAL.modal({keyboard: false, backdrop: 'static'});
    }

    function setEditFormReadOnly(readOnly) {
        const inputIDs = ["start-date-input", "start-date-type-id", "end-date-input", "end-date-type-id", "employee-comment-input", "dayoff-type-select", "submit-button", "delete-button"];
        if (readOnly) {
            for (let inputID of inputIDs) {
                document.getElementById(inputID).setAttribute('disabled', "");
            }
        } else {
            for (let inputID of inputIDs) {
                document.getElementById(inputID).removeAttribute('disabled');
            }
        }
    }

    //Appellé lorsque l'utilisateur appuie sur le bouton Supprimer dans la boite de dialogue pour modifier une demande de congé
    function onDeleteButtonClick() {
        if (confirm("Voulez vous vraiment supprimer cette demande de congés ?")) {
            document.getElementById("dayoff-action-input").value = "delete";
            document.getElementById("edit-form").submit();
        }
    }

    //Appellé lorsque l'utilisateur appuie sur le bouton Annuler dans la boite de dialogue pour modifier une demande de congé
    function onCancelButtonClick() {
        document.getElementById("dayoff-action-input").value = "nothing";
        document.getElementById("edit-form").submit();
    }

    document.addEventListener('DOMContentLoaded', function () {
        calendar = new FullCalendar.Calendar(document.getElementById('calendar'), {
            schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
            eventClick: onCalendarItemClick,
            plugins: ['interaction', 'dayGrid'],
            timeZone: 'UTC',
            defaultView: 'dayGridMonth',
            aspectRatio: 1.5,
            weekends: false,
            editable: false,
            selectable: true,
            locale: 'fr',
            events: events,
            select: onDateRangeSelection
        });

        calendar.render();
    });
</script>