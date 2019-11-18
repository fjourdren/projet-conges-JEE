<%@ page import="fr.enssat.dayoff_manager.db.employee.Employee, fr.enssat.dayoff_manager.db.employee.EmployeeType" %>

<%
Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
%>

<div class="col-md-3">
    <ul class="nav flex-column menuGauche">
        <li class="nav-item">
        	<strong>Menu Salarié</strong>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "recapdayoffs") { %> active <% } %>" href="recapdayoffs"><span class="glyphicon glyphicon-road"></span> Récapitulatif des congés</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "dayoffsAddModify") { %> active <% } %>" href="dayoffsAddModify"><span class="glyphicon glyphicon-road"></span> Ajouter congés</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "dayoffs") { %> active <% } %>" href="comportements"><span class="glyphicon glyphicon-road"></span> Congés</a>
        </li>
        
        <% if(employeeLogged.getType() == EmployeeType.BOSS) { %>
        <li class="nav-item">
        	<strong>Menu chef de service</strong>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "teamdayoffs") { %> active <% } %>" href="teamdayoffs"><span class="glyphicon glyphicon-road"></span> Congés des membres de l'équipe</a>
        </li>
        <% } %>
        
        
        <% if(employeeLogged.getType() == EmployeeType.RH) { %>
        <li class="nav-item">
        	<strong>Menu RH</strong>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "dayoffsRH") { %> active <% } %>" href="dayoffsRH"><span class="glyphicon glyphicon-console"></span> Voir les demandes de congés</a>
        </li>
        <% } %>
        
        
        <% if(employeeLogged.getType() == EmployeeType.RH_ADMIN) { %>
        <li class="nav-item">
        	<strong>Menu Responsable RH</strong>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "typesDayoff") { %> active <% } %>" href="typesDayoff"><span class="glyphicon glyphicon-user"></span> Gestion des types de congés</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "employees") { %> active <% } %>" href="employees"><span class="glyphicon glyphicon-cog"></span> Gestion des employées</a>
        </li>
        <% } %>
    </ul>
</div>