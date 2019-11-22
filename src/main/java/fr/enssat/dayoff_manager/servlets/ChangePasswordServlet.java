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
        name = "ChangePasswordServlet",
        description = "changePassword",
        urlPatterns = {"/changePassword"}
)
public class ChangePasswordServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = request.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null) {
            response.sendRedirect("login");
            return;
        }

        request.setAttribute("componentNeeded", "changePassword");

        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/template/index.jsp");

        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = (String) request.getParameter("password");
        String repeatpassword = (String) request.getParameter("repeatpassword");

        EmployeeDao employeeDao = DaoProvider.getEmployeeDao();

        HttpSession session = request.getSession();
        
        if(password.equals(repeatpassword)) {
        	Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        	employeeLogged.setSha256Password(Utils.sha256(password));
        	
        	session.setAttribute("flashType", "success");
            session.setAttribute("flashMessage", "Mot de passe changé !");

            response.sendRedirect("changePassword");
        } else {
        	session.setAttribute("flashType", "danger");
            session.setAttribute("flashMessage", "Mots de passe différents");

            response.sendRedirect("changePassword");
        }
        
    }
}
