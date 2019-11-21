<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff_type.DayoffType" %>
<%
    DayoffType dayoffType = (DayoffType) request.getAttribute("dayoffType");
%>

<div class="col-md-9">
    <div class="row">
        <nav class="navbar navbar-light bg-light">
            <% if (dayoffType == null) { %>
            <span class="navbar-brand mb-0">Ajouter un type de congés</span>
            <% } else { %>
            <span class="navbar-brand mb-0">Modifier un type de congés</span>
            <% } %>
        </nav>
    </div>
</div

<div class="col-md-9">
    <div class="row">
        <form action="" method="POST" id="edit-form">

            <input type="hidden" name="id" id="object-id" value="<%= dayoffType == null ? "" : dayoffType.getId() %>">
            <div style="margin: 16px">
                <div class="card">
                    <div style="padding: 16px">
                        <div class="form-group row">
                            <label for="name-input" class="col-sm-2 col-form-label">Nom</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="name" id="name-input" required
                                       value="<%= dayoffType == null ? "" : dayoffType.getName() %>">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="nb-days-input" class="col-sm-2 col-form-label">Nombre de jour par défaut</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="nb-days" id="nb-days-input" required
                                       value="<%= dayoffType == null ? "" : dayoffType.getDefaultNbDays() %>">
                            </div>
                        </div>

                    </div>
                </div>

            </div>


            <div class="row">
                <div class="center-button">
                    <button class="btn btn-success" type="submit" style="margin:5px;">Enregistrer</button>
                    <a class="btn btn-primary" style="margin:5px;" href="dayofftype-list" role="button">Annuler</a>
                </div>
            </div>


        </form>
    </div>
</div>