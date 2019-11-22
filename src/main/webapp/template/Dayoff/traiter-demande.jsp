<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff.Dayoff" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.Employee" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.EmployeeType" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff.DayoffStatus" %>
<%
    Dayoff dayoff = (Dayoff) request.getAttribute("dayoff");
    Map<Dayoff, Float> dayoffTypeMap = (Map<Dayoff, Float>) request.getAttribute("dayoffMap");
%>

<div class="col-md-9">
    <div class="row">
        <nav class="navbar navbar-light bg-light">
            <% if (dayoff == null || dayoff.getId() == -1) { %>
            <span class="navbar-brand mb-0">Ajouter une demande</span>
            <% } else { %>
            <span class="navbar-brand mb-0">Traiter une demande</span>
            <% } %>
        </nav>
    </div>
</div>


<div class="col-md-9">
    <div class="row">
        <form action="dayoffsRH-demande" method="POST" id="edit-form">

            <input type="hidden" name="id" id="object-id" value="<%= dayoff.getId() %>">
            <div style="margin: 16px">
                <div class="card">
                    <div style="padding: 16px">
                        <div class="form-group row">
                            <label class="col-sm-12 col-form-label"><%= dayoff.getEmployee().getFirstName()+" "+dayoff.getEmployee().getLastName() %></label>

                        </div>
                        <div class="form-group row">
                            <label class="col-sm-12 col-form-label">Date création de la demande : <%= dayoff.getDateCreation() %></label>

                        </div>
                        <div class="form-group row">
                            <label class="col-sm-12 col-form-label">Date début du congé : <%= dayoff.getDateStart() %></label>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-12 col-form-label">Date début du congé : <%= dayoff.getDateEnd() %></label>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-12 col-form-label">Commentaire : <%= (dayoff.getCommentEmployee() == null)?"":dayoff.getCommentEmployee() %></label>
                        </div>
                        <div class="form-group row">
                            <label for="comment-rh-input" class="col-sm-2 col-form-label">Commentaire RH : </label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="comment-rh" id="comment-rh-input"
                                       value="<%= (dayoff.getCommentRH() == null)?"":dayoff.getCommentRH() %>">
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-4">
                                <input type="radio" id="choix-rh-input-validate" name="choix-rh" value="ACCEPTED" <% if (dayoff.getStatus()==DayoffStatus.ACCEPTED) { %> checked <% } %>>
                                <label for="choix-rh-input-validate">Accepté</label>
                            </div>

                            <div class="col-sm-4">
                                <input type="radio" id="choix-rh-input-wait" name="choix-rh" value="WAITING"<% if (dayoff.getStatus()==DayoffStatus.WAITING) { %> checked <% } %>>
                                <label for="choix-rh-input-wait">En attente</label>
                            </div>

                            <div class="col-sm-4">
                                <input type="radio" id="choix-rh-input-refused" name="choix-rh" value="REFUSED"<% if (dayoff.getStatus()==DayoffStatus.REFUSED) { %> checked <% } %>>
                                <label for="choix-rh-input-refused">Refusé</label>
                            </div>
                        </div>
                    </div>
                </div>

            </div>


            <div class="row">
                <div class="center-button">
                    <button class="btn btn-success" type="submit" style="margin:5px;">Enregistrer</button>
                    <a class="btn btn-primary" style="margin:5px;" href="dayoffsRH" role="button">Annuler</a>
                </div>
            </div>


        </form>
    </div>
</div>