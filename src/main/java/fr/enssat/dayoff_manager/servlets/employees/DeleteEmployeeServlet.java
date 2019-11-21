package fr.enssat.dayoff_manager.servlets.employees;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeDao;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;
import fr.enssat.dayoff_manager.servlets.EnhancedHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Servlet permettant de supprimer un employé
 * <p>
 * URLS:
 * - /employees-delete?id=ID
 */
@WebServlet(
        name = "DeleteEmployeeServlet",
        description = "DeleteEmployeeServlet",
        urlPatterns = {"/employees-delete"}
)
public class DeleteEmployeeServlet extends EnhancedHttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") != null) {
            EmployeeDao employeeDao = DaoProvider.getEmployeeDao();
            Employee employee = null;

            try {
                employee = employeeDao.findById(Long.parseLong(request.getParameter("id")));
            } catch (Exception e) {
                showFlashMessage(request, response, "danger", "Requête invalide");
                response.sendRedirect("employees-list");
                return;
            }

            if (employee != null) {

                try {
                    employeeDao.delete(employee);
                    showFlashMessage(request, response, "success", "Employé supprimé");
                } catch (Exception e) {
                    e.printStackTrace();
                    showFlashMessage(request, response, "danger", "Erreur pendant suppression");
                }

            } else {
                showFlashMessage(request, response, "danger", "Employé inconnu");
            }
        } else {
            showFlashMessage(request, response, "danger", "Requête invalide");
        }

        response.sendRedirect("employees-list");
    }

}
