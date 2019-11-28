package fr.enssat.dayoff_manager.servlets.dayoff;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.department.Department;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;
import fr.enssat.dayoff_manager.servlets.EnhancedHttpServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet permettant d'afficher via une timeline soit
 * - tous les congés
 * - tous les congés des employés d'une équipe
 * <p>
 * URLS:
 * - /dayoff-timeline
 * - /dayoff-timeline?teamName=NOM_EQUIPE
 */
@WebServlet(
        name = "DayoffTimelineServlet",
        description = "DayoffTimelineServlet",
        urlPatterns = {"/dayoff-timeline"}
)
public class DayoffTimelineServlet extends EnhancedHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employee> employees;
        List<Dayoff> dayoffs;

        String teamName = req.getParameter("teamName");
        if (teamName != null) {
            Department department = DaoProvider.getDepartmentDao().findByName(teamName);
            if (department != null) {
                Employee loggedUser = getLoggedUser(req.getSession());
                // Le chef d'équipe ne peut voir que son équipe
                if (loggedUser.getType() == EmployeeType.BOSS && !loggedUser.getDepartment().getName().equals(teamName)) {
                    showFlashMessage(req, resp, "danger", "Vous ne pouvez voir que les congés de votre équipe");
                    resp.sendRedirect("default");
                    return;
                }

                employees = DaoProvider.getDepartmentDao().getEmployees(department);
                dayoffs = DaoProvider.getDepartmentDao().getDayOffs(department);
            } else {
                showFlashMessage(req, resp, "danger", "Equipe inconnue");
                resp.sendRedirect("default");
                return;
            }
        } else {
            // Le chef d'équipe ne peut voir que son équipe
            if (getLoggedUser(req.getSession()).getType() == EmployeeType.BOSS) {
                showFlashMessage(req, resp, "danger", "Vous ne pouvez voir que les congés de votre équipe");
                resp.sendRedirect("default");
                return;
            }

            employees = DaoProvider.getEmployeeDao().getAll();
            dayoffs = DaoProvider.getDayoffDao().getAll();
        }

        req.setAttribute("componentNeeded", "dayoffTimeline");
        req.setAttribute("employees", employees);
        req.setAttribute("dayoffs", dayoffs);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/template/index.jsp");
        dispatcher.forward(req, resp);
    }
}
