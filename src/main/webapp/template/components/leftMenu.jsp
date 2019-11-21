<%@ page import="fr.enssat.dayoff_manager.db.employee.Employee, fr.enssat.dayoff_manager.db.employee.EmployeeType" %>

<%
Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
if(employeeLogged != null) {
%>

<div class="col-md-3">
    <ul class="nav flex-column menuGauche">
        <li class="nav-item">
        	<strong>Menu Salari�</strong>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "recapdayoffs") { %> active <% } %>" href="recapdayoffs"><span class="glyphicon glyphicon-folder-open"></span> R�capitulatif des cong�s</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "dayoffsAddModify") { %> active <% } %>" href="dayoffsAddModify"><span class="glyphicon glyphicon-plus"></span> Ajouter cong�s</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "dayoffs") { %> active <% } %>" href="dayoffs"><span class="glyphicon glyphicon-list-alt"></span> Cong�s</a>
        </li>
        
        <% if(employeeLogged.getType() == EmployeeType.BOSS) { %>
        <li class="nav-item">
        	<strong>Menu chef de service</strong>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "teamdayoffs") { %> active <% } %>" href="teamdayoffs"><span class="glyphicon glyphicon-calendar"></span> Cong�s des membres de l'�quipe</a>
        </li>
        <% } %>
        
        
        <% if(employeeLogged.getType() == EmployeeType.RH || employeeLogged.getType() == EmployeeType.RH_ADMIN) { %>
        <li class="nav-item">
        	<strong>Menu RH</strong>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "dayoffsRH") { %> active <% } %>" href="dayoffsRH"><span class="glyphicon glyphicon-calendar"></span> Voir les demandes de cong�s</a>
        </li>
        <% } %>
        
        
        <% if(employeeLogged.getType() == EmployeeType.RH_ADMIN) { %>
        <li class="nav-item">
        	<strong>Menu Responsable RH</strong>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "typesDayoff") { %> active <% } %>" href="congesTypes"><span class="glyphicon glyphicon-book"></span> Gestion des types de cong�s</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "employees") { %> active <% } %>" href="employees"><span class="glyphicon glyphicon-user"></span> Gestion des employ�es</a>
        </li>
        <% } %>
    </ul>
</div>

<%
}
%>