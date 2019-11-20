package fr.enssat.dayoff_manager.servlets;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.department.Department;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "DayoffTimelineServlet",
        description = "DayoffTimelineServlet",
        urlPatterns = {"/dayoff-timeline"}
)
public class DayoffTimelineServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = req.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null || !(employeeLogged.getType() == EmployeeType.RH_ADMIN || employeeLogged.getType() == EmployeeType.RH)) {
            resp.sendRedirect("default");
            return;
        }

        List<Employee> employees;
        List<Dayoff> dayoffs;

        if (req.getParameter("teamName") != null) {
            Department department = DaoProvider.getDepartmentDao().findByName(req.getParameter("teamName"));
            if (department != null) {
                employees = DaoProvider.getDepartmentDao().getEmployees(department);
                dayoffs = DaoProvider.getDepartmentDao().getDayOffs(department);
            } else {
                session.setAttribute("flashType", "danger");
                session.setAttribute("flashMessage", "Equipe inconnue");
                resp.sendRedirect("default");
                return;
            }
        } else {
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
