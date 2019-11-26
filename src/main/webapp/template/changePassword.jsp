<%@ page pageEncoding="UTF-8" %>
<div class="container">
    <div class="row">
        <div class="mx-auto col-md-6">
            <form action="" method="POST">
                <div class="form-group">
                    <label for="password">Nouveau mot de passe :</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>

                <div class="form-group">
                    <label for="repeatpassword">Répéter votre nouveau mot de passe :</label>
                    <input type="password" class="form-control" id="repeatpassword" name="repeatpassword" required>
                </div>

                <button type="submit" class="btn btn-success">Valider</button>
            </form>
        </div>
    </div>
</div>
