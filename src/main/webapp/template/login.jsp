<%@ page pageEncoding="UTF-8" %>
<div class="container">
    <div class="row">
        <div class="mx-auto col-md-6">
            <form action="login" method="POST">
                <div class="form-group">
                    <label for="email">Adresse email :</label>
                    <input type="email" class="form-control" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="password">Mot de passe :</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>

                <button type="submit" class="btn btn-success">Connexion</button>
            </form>
        </div>
    </div>
</div>