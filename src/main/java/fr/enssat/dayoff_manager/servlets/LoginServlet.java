package fr.enssat.dayoff_manager.servlets;

import fr.enssat.dayoff_manager.Utils;
import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        name = "LoginServlet",
        description = "Login",
        urlPatterns = {"/login"}
)
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = request.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged != null) {
            response.sendRedirect("default");
            return;
        }

        // login page
        request.setAttribute("componentNeeded", "login");

        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/template/index.jsp");

        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        EmployeeDao employeeDao = DaoProvider.getEmployeeDao();

        //FIXME [Clément] Maintenant le password est chiffré en SHA256, il faut penser à le chiffrer via Utils.sha256
        Employee employeeLogin = employeeDao.login(email, Utils.sha256(password));

        HttpSession session = request.getSession();

        if (employeeLogin != null) {

            session.setAttribute("employeeLogged", employeeLogin);

            response.sendRedirect("default");
        } else {
            session.setAttribute("flashType", "danger");
            session.setAttribute("flashMessage", "Authentification invalide.");

            response.sendRedirect("login");
        }
    }
}
