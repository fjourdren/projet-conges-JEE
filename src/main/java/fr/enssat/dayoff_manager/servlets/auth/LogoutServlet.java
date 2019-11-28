package fr.enssat.dayoff_manager.servlets.auth;

import fr.enssat.dayoff_manager.servlets.EnhancedHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet permettant à un utilisateur de se desauthentifier
 * URL: /logout
 */
@WebServlet(
        name = "LogoutServlet",
        description = "Logout",
        urlPatterns = {"/logout"}
)
public class LogoutServlet extends EnhancedHttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setLoggedUser(request.getSession(), null);
        showFlashMessage(request, response, "success", "Vous avez été déconnecté.");
        response.sendRedirect("login");
    }

}
