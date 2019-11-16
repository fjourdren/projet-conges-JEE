<div class="col-md-3">
    <ul class="nav flex-column menuGauche">
        <li class="nav-item">
        	<strong>Menu Salarié</strong>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "departments") { %> active <% } %>" href="departments"><span class="glyphicon glyphicon-user"></span> Services</a>
        </li>
        <li class="nav-item">
        	<strong>Menu Ressources Humaines</strong>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "todo") { %> active <% } %>" href="commandes"><span class="glyphicon glyphicon-console"></span> Commandes</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "todo") { %> active <% } %>" href="commandes-grades"><span class="glyphicon glyphicon-list-alt"></span> Liaisons Grades / Commandes</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "todo") { %> active <% } %>" href="comportements"><span class="glyphicon glyphicon-road"></span> Comportements</a>
        </li>
        <li class="nav-item">
            <a class="nav-link <% if((String) request.getAttribute("leftMenuActive") == "todo") { %> active <% } %>" href="logs"><span class="glyphicon glyphicon-cog"></span> Configuration des Logs</a>
        </li>
    </ul>
</div>