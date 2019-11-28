<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff.Dayoff" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff.DayoffStatus" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Dayoff e = (Dayoff) request.getAttribute("dayoff");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
%>


<form action="" method="POST" id="edit-form">
    <nav class="navbar navbar-light bg-light">
        <span class="navbar-brand mb-0 h1">Traiter une demande de congés</span>
        <div>
            <button class="btn btn-primary my-2 my-sm-0" type="submit">Enregistrer</button>
            <a class="btn btn-outline-success my-2 my-sm-0" href="rh-dayoff-list">Annuler</a>
        </div>
    </nav>

    <input type="hidden" name="id" id="object-id" value="<%= e == null ? "" : e.getId() %>">

    <div style="margin: 16px">
        <div class="card">
            <div style="padding: 16px">

                <div class="form-group row">
                    <label class="col-sm-12 col-form-label">
                        <%= e.getEmployee().getFirstName() + " " + e.getEmployee().getLastName() %>
                    </label>
                </div>

                <div class="form-group row">
                    <label class="col-sm-12 col-form-label">
                        Date création : <%= dateFormat.format(e.getDateCreation()) %>
                    </label>
                </div>

                <div class="form-group row">
                    <label class="col-sm-12 col-form-label">
                        Date début : <%= dateFormat.format(e.getDateStart()) %>
                    </label>
                </div>

                <div class="form-group row">
                    <label class="col-sm-12 col-form-label">
                        Date fin : <%= dateFormat.format(e.getDateEnd()) %>
                    </label>
                </div>

                <div class="form-group row">
                    <label class="col-sm-12 col-form-label">
                        Durée : <%= e.getNbDays() %> jours
                    </label>
                </div>

                <div class="form-group row">
                    <label class="col-sm-12 col-form-label">
                        Type : <%= e.getType().getName() %>
                    </label>
                </div>

                <div class="form-group row">
                    <label class="col-sm-12 col-form-label">
                        Commentaire employé : <%= e.getCommentEmployee() %>
                    </label>
                </div>

                <div class="form-group row">
                    <label for="comment-rh-input" class="col-sm-2 col-form-label">Commentaire RH : </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="comment-rh" id="comment-rh-input"
                               value="<%= (e.getCommentRH() == null)?"":e.getCommentRH() %>">
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-sm-4">
                        <input type="radio" id="choix-rh-input-validate" name="choix-rh"
                               value="ACCEPTED" <% if (e.getStatus()==DayoffStatus.ACCEPTED) { %>
                               checked <% } %>>
                        <label for="choix-rh-input-validate">Accepté</label>
                    </div>

                    <div class="col-sm-4">
                        <input type="radio" id="choix-rh-input-wait" name="choix-rh"
                               value="WAITING"<% if (e.getStatus()==DayoffStatus.WAITING) { %>
                               checked <% } %>>
                        <label for="choix-rh-input-wait">En attente</label>
                    </div>

                    <div class="col-sm-4">
                        <input type="radio" id="choix-rh-input-refused" name="choix-rh"
                               value="REFUSED"<% if (e.getStatus()==DayoffStatus.REFUSED) { %>
                               checked <% } %>>
                        <label for="choix-rh-input-refused">Refusé</label>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
