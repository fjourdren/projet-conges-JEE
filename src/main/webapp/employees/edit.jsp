<%--suppress unchecked --%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.db.dayoff_type.DayoffType" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.Employee" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.EmployeeType" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
    Employee employee = (Employee) request.getAttribute("employee");
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
        <% if (employee.getId() == -1) { %>
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

    <input type="hidden" name="id" id="object-id" value="<%= employee.getId() %>">
    <div style="margin: 16px">
        <div class="card">
            <div style="padding: 16px">
                <div class="form-group row">
                    <label for="last-name-input" class="col-sm-2 col-form-label">Nom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="last-name" id="last-name-input" required
                               value="<%= employee.getLastName() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="first-name-input" class="col-sm-2 col-form-label">Prénom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="first-name" id="first-name-input" required
                               value="<%= employee.getFirstName() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="email-input" class="col-sm-2 col-form-label">Email</label>
                    <div class="col-sm-10">
                        <input type="email" class="form-control" name="email" id="email-input" required
                               value="<%= employee.getEmail() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="password-input" class="col-sm-2 col-form-label">Mot de passe</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" name="password" id="password-input" required
                               value="<%= employee.getPassword() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="adress-input" class="col-sm-2 col-form-label">Adresse</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="adress" id="adress-input" required
                               value="<%= employee.getAddress() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="position-input" class="col-sm-2 col-form-label">Fonction</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="position" id="position-input" required
                               value="<%= employee.getPosition() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="department-input" class="col-sm-2 col-form-label">Service</label>
                    <div class="col-sm-10">
                        <input list="departments" type="text" class="form-control" name="department"
                               id="department-input" required
                               value="<%= employee.getDepartment().getName() %>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="department-input" class="col-sm-2 col-form-label">Service</label>
                    <div class="col-sm-10">
                        <select name="type" id="type-select" required>
                            <!-- from EmployeeType -->
                            <option value="CLASSIC" <%= (employee.getType() == EmployeeType.CLASSIC) ? "selected" : "" %>
                                    selected="<%= employee.getType() == EmployeeType.CLASSIC ? "selected" : ""%>">
                                Employée
                            </option>
                            <option value="BOSS" <%= (employee.getType() == EmployeeType.BOSS) ? "selected" : "" %>>Chef
                                d'équipe
                            </option>
                            <option value="RH" <%= (employee.getType() == EmployeeType.RH) ? "selected" : "" %>>
                                Membre RH
                            </option>
                            <option value="RH_ADMIN"
                                    <%= (employee.getType() == EmployeeType.RH_ADMIN) ? "selected" : "" %>>Admin
                                RH
                            </option>
                        </select>
                    </div>
                </div>
            </div>
        </div>

        <br>

        <div class="table card">
            <table class=" table table-hover">
                <thead>
                <tr>
                    <th scope="col">Type</th>
                    <th scope="col">Nbre jours congés (-1 = pas de limitation)</th>
                </tr>
                </thead>
                <tbody>
                <% for (Map.Entry<DayoffType, Float> dayoffTypeEntry : dayoffTypeMap.entrySet()) {%>
                <tr>
                    <td><%= dayoffTypeEntry.getKey().getName() %>
                    </td>
                    <td><input type="number" min="-1" step="0.5" class="form-control"
                               name="<%= "dayofftype-" + dayoffTypeEntry.getKey().getId() %>"
                               id="<%= "dayofftype-" + dayoffTypeEntry.getKey().getId() + "-input" %>" required
                               value="<%= dayoffTypeEntry.getValue() == null ? -1 : dayoffTypeEntry.getValue() %>">
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