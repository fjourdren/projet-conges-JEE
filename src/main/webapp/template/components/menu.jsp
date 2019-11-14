<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">Titre</a>
        </div>

		<ul class="nav navbar-nav">
		    <li><p>Sélection du serveur :</p></li>
		</ul>


		<ul class="nav navbar-nav navbar-right">
            <% 
            String email = (String) session.getAttribute("email");
            if(email != null && !email.isEmpty()) { 
            %>
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><img class="img-circle" style="height: 19px; padding: 0; margin: 0;" src="https://cours-informatique-gratuit.fr/wp-content/uploads/2014/05/compte-utilisateur-1.png" alt=""/> <%= session.getAttribute("prenom") %> <%= session.getAttribute("nom") %> <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Déconnexion</a></li>
					</ul>
				</li>
			<% } else { %>
				<li><a href="login"><span class="glyphicon glyphicon-user"></span> Connexion</a></li>
			<% } %>
        </ul>
    </div>
</nav>