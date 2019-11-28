package fr.enssat.dayoff_manager.servlets.auth;

import fr.enssat.dayoff_manager.Utils;
import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeDao;
import fr.enssat.dayoff_manager.servlets.EnhancedHttpServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet permettant à un utilisateur de s'authentifier
 * URL: /login
 */
@WebServlet(
        name = "LoginServlet",
        description = "Login",
        urlPatterns = {"/login"}
)
public class LoginServlet extends EnhancedHttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (getLoggedUser(request.getSession()) != null) {
            response.sendRedirect("default");
            return;
        }

        // login page
        request.setAttribute("componentNeeded", "login");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/template/index.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        EmployeeDao employeeDao = DaoProvider.getEmployeeDao();

        Employee employeeLogin = employeeDao.login(email, Utils.sha256(password));
        if (employeeLogin != null) {
            setLoggedUser(request.getSession(), employeeLogin);
            response.sendRedirect("default");
        } else {
            showFlashMessage(request, response, "danger", "Authentification invalide.");
            response.sendRedirect("login");
        }
    }
}
