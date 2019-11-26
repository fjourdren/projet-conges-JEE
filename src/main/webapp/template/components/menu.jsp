<%@ page pageEncoding="UTF-8" %>
<%@ page import="fr.enssat.dayoff_manager.Constants" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.Employee" %>
<%@ page import="fr.enssat.dayoff_manager.db.employee.EmployeeType" %>

<%
    Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
    String endpoint = (String) session.getAttribute(Constants.CURRENT_ENDPOINT_SESSION_ATTRIBUTE_NAME);
%>

<nav class="navbar navbar-expand-lg bg-dark navbar-dark">
    <a class="navbar-brand" href="#">DayoffManager</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>


    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <% if (employeeLogged != null) { %>

        <ul class="navbar-nav mr-auto">

            <!-- RH_ADMIN -->
            <% if (employeeLogged.getType() == EmployeeType.RH_ADMIN) { %>
            <li class="nav-item">
                <a class="nav-link <%= endpoint.equals("employees-list") ? "active":"" %>" href="employees-list">Employés</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= endpoint.equals("dayofftype-list") ? "active":"" %>" href="dayofftype-list">Types
                    de congés</a>
            </li>
            <% } %>


            <!-- RH -->
            <% if (employeeLogged.getType() == EmployeeType.RH ||
                    employeeLogged.getType() == EmployeeType.RH_ADMIN) { %>
            <li class="nav-item">
                <a class="nav-link <%= endpoint.equals("rh-dayoff-list") ? "active":"" %>" href="rh-dayoff-list">Validation
                    congés</a>
            </li>
            <li class="nav-item">
                <a class="nav-link <%= endpoint.equals("dayoff-timeline") ? "active":"" %>" href="dayoff-timeline">Visualisation
                    congés</a>
            </li>
            <% } %>

            <!-- BOSS -->
            <% if (employeeLogged.getType() == EmployeeType.BOSS) { %>
            <li class="nav-item">
                <a class="nav-link <%= endpoint.equals("dayoff-timeline") ? "active":"" %>"
                   href="dayoff-timeline?teamName=<%= employeeLogged.getDepartment().getName() %>">
                    Congés équipe
                </a>
            </li>
            <% } %>

            <!-- CLASSIC -->
            <% if (employeeLogged.getType() == EmployeeType.CLASSIC ||
                    employeeLogged.getType() == EmployeeType.BOSS ||
                    employeeLogged.getType() == EmployeeType.RH ||
                    employeeLogged.getType() == EmployeeType.RH_ADMIN) { %>
            <li class="nav-item">
                <a class="nav-link <%= endpoint.equals("manage-my-dayoffs") ? "active":"" %>" href="manage-my-dayoffs">Mes
                    congés</a>
            </li>
            <% } %>

        </ul>

        <ul class="navbar-nav">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <%= employeeLogged.getFirstName() + " " + employeeLogged.getLastName() %>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="change-password">Changer mot de passe</a>
                    <a class="dropdown-item" href="logout">Déconnexion</a>
                </div>
            </li>
        </ul>

        <% } %>

    </div>
</nav>
