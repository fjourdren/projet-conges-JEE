<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff_type.DayoffType" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.Employee" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.EmployeeType" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
    Employee e;
    if (request.getAttribute("employee") == null) {
        e = null;
    } else {
        e = (Employee) request.getAttribute("employee");
    }

    Map<DayoffType, Float> dayoffTypeMap = (Map<DayoffType, Float>) request.getAttribute("dayoffTypeMap");
    List<String> allDeps = (List<String>) request.getAttribute("allDeps");
%>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>

    <!-- jquery -->
    <script src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>

    <!-- boostrap -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>

<body>
<form action="" method="POST" id="edit-form">
    <nav class="navbar navbar-light bg-light">
        <% if (e == null) { %>
        <span class="navbar-brand mb-0 h1">Ajouter un employé</span>
        <% } else { %>
        <span class="navbar-brand mb-0 h1">Modifier un employé</span>
        <% } %>

        <div>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Enregistrer</button>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Annuler</button>
        </div>
    </nav>

    <!-- department autocompletion -->
    <datalist id="departments">
        <% for (String depName : allDeps) { %>
        <option value="<%= depName %>"></option>
        <% } %>
    </datalist>

    <input type="hidden" name="id" id="object-id" value="<%= e == null ? "" : e.getId() %>">
    <div style="margin: 16px">
        <div class="card">
            <div style="padding: 16px">
                <div class="form-group row">
                    <label for="last-name-input" class="col-sm-2 col-form-label">Nom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="last-name" id="last-name-input" required
                               value="<%= e == null ? "" : e.getLastName() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="first-name-input" class="col-sm-2 col-form-label">Prénom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="first-name" id="first-name-input" required
                               value="<%= e == null ? "" : e.getFirstName() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="email-input" class="col-sm-2 col-form-label">Email</label>
                    <div class="col-sm-10">
                        <input type="email" class="form-control" name="email" id="email-input" required
                               value="<%= e == null ? "" : e.getEmail() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="password-input" class="col-sm-2 col-form-label">Mot de passe</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" name="password" id="password-input" required
                               value="<%= e == null ? "" : e.getSha256Password() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="adress-input" class="col-sm-2 col-form-label">Adresse</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="adress" id="adress-input" required
                               value="<%= e == null ? "" : e.getAddress() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="position-input" class="col-sm-2 col-form-label">Fonction</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="position" id="position-input" required
                               value="<%= e == null ? "" : e.getPosition() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="department-input" class="col-sm-2 col-form-label">Service</label>
                    <div class="col-sm-10">
                        <input list="departments" type="text" class="form-control" name="department"
                               id="department-input" required
                               value="<%= e == null ? "" : e.getDepartment().getName() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="department-input" class="col-sm-2 col-form-label">Type</label>
                    <div class="col-sm-10">
                        <select name="type" id="type-select" required>
                            <!-- from EmployeeType -->
                            <option
                                    value="CLASSIC"
                                    <%= (e != null && e.getType() == EmployeeType.CLASSIC) ? "selected" : "" %>>
                                Employée
                            </option>
                            <option
                                    value="BOSS"
                                    <%= (e != null && e.getType() == EmployeeType.BOSS) ? "selected" : "" %>>Chef
                                d'équipe
                            </option>
                            <option
                                    value="RH"
                                    <%= (e != null && e.getType() == EmployeeType.RH) ? "selected" : "" %>>
                                Membre RH
                            </option>
                            <option
                                    value="RH_ADMIN"
                                    <%= (e != null && e.getType() == EmployeeType.RH_ADMIN) ? "selected" : "" %>>Admin
                                RH
                            </option>
                        </select>
                    </div>
                </div>
            </div>
        </div>

        <br>

        <div class="table card">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Type</th>
                    <th scope="col">Nombre jours congés</th>
                </tr>
                </thead>
                <tbody>
                <% for (Map.Entry<DayoffType, Float> entry : dayoffTypeMap.entrySet()) {%>
                <tr>
                    <td><%= entry.getKey().getName() %>
                    </td>
                    <td>
                        <div class="form-group">
                            <div class="input-group mb-2">
                                <input type="number" class="form-control"
                                       min="0"
                                       step="0.5"
                                    <%= (entry.getValue() == null) ? "disabled" : "" %>
                                       name="<%= "dayofftype-" + entry.getKey().getId() %>"
                                       id="<%= "dayofftype-" + entry.getKey().getId() + "-input" %>"
                                       value="<%= entry.getValue() == null ? "" : entry.getValue() %>">
                                <span class="input-group-text">
                                    <input type="checkbox"
                                           name="<%= "dayofftype-" + entry.getKey().getId() + "-unlimited" %>"
                                           id="<%= "dayofftype-" + entry.getKey().getId() + "-unlimited-chk" %>"
                                           onclick="var a = document.getElementById('<%= "dayofftype-" + entry.getKey().getId() + "-input" %>'); a.disabled = this.checked; a.required = !this.checked;"
                                        <%= (entry.getValue() == null) ? "checked" : "" %>
                                    >
                                    <label style="margin-bottom:0; margin-left: 4px"
                                           for="<%= "dayofftype-" + entry.getKey().getId() + "-unlimited-chk" %>">Illimité</label>
                                </span>
                            </div>
                        </div>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

</form>

</body>
</html>