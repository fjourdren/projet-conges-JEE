package fr.enssat.dayoff_manager.servlets.employees;

import fr.enssat.dayoff_manager.Utils;
import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCount;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
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
import java.util.*;

/**
 * Servlet permettant d'ajouter ou de modifier un employé
 * <p>
 * URLS:
 * - /employees-add-edit
 * - /employees-add-edit?id=ID
 */
@WebServlet(
        name = "EditEmployeeServlet",
        description = "EditEmployeeServlet",
        urlPatterns = {"/employees-add-edit"}
)
public class EditEmployeeServlet extends EnhancedHttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = null;
        List<String> allDeps = new ArrayList<>();
        Map<DayoffType, Float> dayoffTypeMap = new HashMap<>();
        for (Department department : DaoProvider.getDepartmentDao().getAll()) {
            allDeps.add(department.getName());
        }

        if (req.getParameter("id") == null) {
            //Nouvel employé
            for (DayoffType type : DaoProvider.getDayoffTypeDao().getAvailableDayoffTypes()) {
                dayoffTypeMap.put(type, type.getDefaultNbDays());
            }
        } else {
            //Modification employé existant
            Long employeeID = -1L;

            try {
                employeeID = Long.parseLong(req.getParameter("id"));
                if (employeeID < 0) throw new IllegalArgumentException("employeeID");
            } catch (Exception e) {
                showFlashMessage(req, resp, "danger", "Requête invalide");
                resp.sendRedirect("employees-list");
                return;
            }

            employee = DaoProvider.getEmployeeDao().findById(employeeID);
            if (employee == null) {
                showFlashMessage(req, resp, "danger", "Employé inconnu");
                resp.sendRedirect("employees-list");
                return;
            }

            for (DayoffType type : DaoProvider.getDayoffTypeDao().getAvailableDayoffTypes()) {
                dayoffTypeMap.put(type, DaoProvider.getEmployeeDao().nbDaysUsable(employee, type));
            }
        }

        req.setAttribute("employee", employee);
        req.setAttribute("allDeps", allDeps);
        req.setAttribute("dayoffTypeMap", dayoffTypeMap);
        req.setAttribute("componentNeeded", "employeesEditAdd");

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/template/index.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Employee employee = createEmployeeObjectFromForm(req);
            updateDayoffCountsFromForm(req, employee);
            showFlashMessage(req, resp, "success", "Employé enregistré");
        } catch (Exception e) {
            e.printStackTrace();
            showFlashMessage(req, resp, "danger", "Erreur pendant enregistrement employé");
        }

        resp.sendRedirect("employees-list");
    }

    /**
     * Crée un objet Employee (et un Department) et les sauvegarde en base
     *
     * @param req http request
     */
    private Employee createEmployeeObjectFromForm(HttpServletRequest req) {
        Employee employee;

        if (!req.getParameter("id").isEmpty()) {
            employee = DaoProvider.getEmployeeDao().findById(Long.parseLong(req.getParameter("id")));
        } else {
            employee = new Employee();
        }

        employee.setLastName(req.getParameter("last-name"));
        employee.setFirstName(req.getParameter("first-name"));
        employee.setEmail(req.getParameter("email"));
        employee.setSha256Password(Utils.sha256(req.getParameter("password")));
        employee.setAddress(req.getParameter("adress"));
        employee.setPosition(req.getParameter("position"));
        employee.setType(EmployeeType.valueOf(req.getParameter("type")));

        String departmentName = req.getParameter("department");
        Department dep = DaoProvider.getDepartmentDao().findByName(departmentName);
        if (dep == null) {
            dep = new Department(departmentName);
            DaoProvider.getDepartmentDao().save(dep);
        }

        employee.setDepartment(dep);
        DaoProvider.getEmployeeDao().save(employee);

        return employee;
    }

    /**
     * Met à jour les nombre de jours disponibles pour chaque congé pour l'employé
     *
     * @param req      http request
     * @param employee employé
     */
    private void updateDayoffCountsFromForm(HttpServletRequest req, Employee employee) {
        for (DayoffType type : DaoProvider.getDayoffTypeDao().getAvailableDayoffTypes()) {
            Float nbDays;
            if (req.getParameter("dayofftype-" + type.getId() + "-unlimited") != null) {
                nbDays = null;
            } else {
                nbDays = Float.parseFloat(req.getParameter("dayofftype-" + type.getId()));
            }

            DayoffCount dayoffCount = DaoProvider.getDayoffCountDao().findByEmployeeAndDayoffType(employee, type);
            if (dayoffCount != null && Objects.equals(type.getDefaultNbDays(), nbDays)) {
                DaoProvider.getDayoffCountDao().delete(dayoffCount);
            } else if (dayoffCount != null) {
                dayoffCount.setNbDays(nbDays);
                DaoProvider.getDayoffCountDao().save(dayoffCount);
            } else {
                dayoffCount = new DayoffCount(nbDays, type, employee);
                DaoProvider.getDayoffCountDao().save(dayoffCount);
            }
        }
    }
}
