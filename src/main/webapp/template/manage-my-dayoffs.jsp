<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff.Dayoff" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff_type.DayoffType" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%
    List<Dayoff> dayoffs = (List<Dayoff>) request.getAttribute("dayoffs");
    List<DayoffType> dayoffTypes = (List<DayoffType>) request.getAttribute("dayoffTypes");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
%>

<style>
    #calendar {
        max-width: 900px;
        margin: 40px auto;
    }
</style>

<div id='calendar'></div>

<div id="myModal" class="modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <form action="" method="post">
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
                        Etat : en cours de création
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class=" mr-auto btn btn-danger" data-dismiss="modal">Supprimer</button>
                    <input type="submit" value="ENVOYER">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
                    <button type="button" class="btn btn-primary" id="okBTN" data-dismiss="modal">Valider</button>
                </div>
            </div>
        </form>
    </div>
</div>


<script>
    var events = [
        <% for (Dayoff dayoff : dayoffs) { %>
        {
            title: "TODO",
            start: "<%= dateFormat.format(dayoff.getDateStart()) %>",
            end: "<%= dateFormat.format(dayoff.getDateEnd()) %>"
        },
        <% } %>
    ];

</script>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        $('#okBTN').on('click', function () {
            alert("HELLO");
            const evt = calendar.getEventById(newEventID);
            evt.setStart(document.getElementById("startDate").value);
            evt.setEnd(document.getElementById("endDate").value);

        })

        function onDateSelection(data) {
            console.log(data);
            //data.start
            //data.end

            newEventID = Math.random().toString();
            calendar.unselect();
            calendar.addEvent({
                id: newEventID,
                title: 'my new event' + Math.random(),
                start: data.start,
                end: data.end,
                allDay: true
            });

            $('#myModal').modal({
                keyboard: false
            })

            document.getElementById("start-date-input").value = data.startStr;
            document.getElementById("end-date-input").value = data.endStr;
        }


        var calendarEl = document.getElementById('calendar');

        var calendar = new FullCalendar.Calendar(calendarEl, {
            schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
            plugins: ['interaction', 'dayGrid'],
            timeZone: 'UTC',
            defaultView: 'dayGridMonth',
            aspectRatio: 1.5,
            weekends: false,
            editable: false,
            selectable: true,
            locale: 'fr',
            events: events,
            select: onDateSelection
        });

        calendar.render();
    });
</script>