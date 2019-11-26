<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff_type.DayoffType" %>
<%
    DayoffType e = (DayoffType) request.getAttribute("dayoffType");
%>


<form action="" method="POST" id="edit-form">
    <nav class="navbar navbar-light bg-light">
        <% if (e == null) { %>
        <span class="navbar-brand mb-0 h1">Ajouter un type de congés</span>
        <% } else { %>
        <span class="navbar-brand mb-0 h1">Modifier un type de congés</span>
        <% } %>

        <div>
            <button class="btn btn-primary my-2 my-sm-0" type="submit">Enregistrer</button>
            <a class="btn btn-outline-success my-2 my-sm-0" href="employees-add-edit">Annuler</a>
        </div>
    </nav>

    <input type="hidden" name="id" id="object-id" value="<%= e == null ? "" : e.getId() %>">
    <div style="margin: 16px">
        <div class="card">
            <div style="padding: 16px">
                <div class="form-group row">
                    <label for="name-input" class="col-sm-2 col-form-label">Nom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="name" id="name-input" required
                               value="<%= e == null ? "" : e.getName() %>">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group mb-2">
                        <input type="number" class="form-control" min="0" step="0.5"
                            <%= (e != null && e.getDefaultNbDays() == null) ? "disabled" : "" %>
                               name="nb-days" id="nb-days-input"
                               value="<%= e != null && e.getDefaultNbDays() == null ? "" : e.getDefaultNbDays() %>">

                        <span class="input-group-text">
                                    <input type="checkbox" name="nb-days-unlimited" id="nb-days-unlimited-input"
                                           onclick="var a = document.getElementById('nb-days-input'); a.disabled = this.checked; a.required = !this.checked;"
                                        <%= (e != null && e.getDefaultNbDays() == null) ? "checked" : "" %>
                                    >
                                    <label style="margin-bottom:0; margin-left: 4px"
                                           for="<%= "nb-days-unlimited" %>">Illimité</label>
                                </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

