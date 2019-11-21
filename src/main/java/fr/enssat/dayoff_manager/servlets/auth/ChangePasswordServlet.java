package fr.enssat.dayoff_manager.servlets.auth;

import fr.enssat.dayoff_manager.Utils;
import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.servlets.EnhancedHttpServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet permettant à un utilisateur de modifier son mot de passe
 * URL: /change-password
 */
@WebServlet(
        name = "ChangePasswordServlet",
        description = "changePassword",
        urlPatterns = {"/change-password"}
)
public class ChangePasswordServlet extends EnhancedHttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("componentNeeded", "changePassword");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/template/index.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String repeatpassword = request.getParameter("repeatpassword");

        //Vérification mots de passe
        if (!password.equals(repeatpassword)) {
            showFlashMessage(request, response, "danger", "Mots de passe différents");
            response.sendRedirect("change-password");
        }

        //Enregistrement nouveau mot de passe
        try {
            Employee employee = DaoProvider.getEmployeeDao().findById(getLoggedUser(request.getSession()).getId());
            employee.setSha256Password(Utils.sha256(password));
            DaoProvider.getEmployeeDao().save(employee);
            setLoggedUser(request.getSession(), employee);
            showFlashMessage(request, response, "success", "Mot de passe changé !");
            response.sendRedirect("default");
        } catch (Exception e) {
            e.printStackTrace();
            showFlashMessage(request, response, "danger", "Erreur pendant modification mot de passe");
            response.sendRedirect("change-password");
        }
    }
}
