<div class="row">
	<div class="col-centered col-md-5">
		<form action="changePassword" method="POST">
			<div class="form-group">
				<label for="password">Nouveau mot de passe :</label>
				<input type="password" class="form-control" id="password" name="password" required>
			</div>
			
			<div class="form-group">
				<label for="repeatpassword">Répéter votre nouveau mot de passe :</label>
				<input type="password" class="form-control" id="repeatpassword" name="repeatpassword" required>
			</div>
			
			<button type="submit" class="btn btn-success">Modifier</button>
		</form>
	</div>
</div>