package fr.enssat.dayoff_manager.servlets.employees;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCount;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.department.Department;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditEmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = new Employee();
        List<String> allDeps = new ArrayList<>();
        Map<DayoffType, Float> dayoffTypeMap = new HashMap<>();

        for (Department department : DaoProvider.getDepartmentDao().getAll()) {
            allDeps.add(department.getName());
        }

        if (req.getParameter("id") == null) {
            //Nouvel employé
            employee.setId(-1);
            employee.setDepartment(new Department());
            for (DayoffType type : DaoProvider.getDayoffTypeDao().getAll()) {
                dayoffTypeMap.put(type, type.getDefaultNbDays());
            }
        } else {
            //Modification employé existant
            int employeeID = -1;

            try {
                employeeID = Integer.parseInt(req.getParameter("id"));
                if (employeeID < 0) throw new IllegalArgumentException("employeeID");
            } catch (Exception e) {
                resp.sendError(400, "INVALID REQUEST");
                return;
            }

            employee = DaoProvider.getEmployeeDao().findById(employeeID);
            if (employee == null) {
                resp.sendError(404, "NOT FOUND");
                return;
            }

            for (DayoffType type : DaoProvider.getDayoffTypeDao().getAll()) {
                dayoffTypeMap.put(type, DaoProvider.getEmployeeDao().nbDaysUsable(employee, type));
            }
        }

        req.setAttribute("employee", employee);
        req.setAttribute("allDeps", allDeps);
        req.setAttribute("dayoffTypeMap", dayoffTypeMap);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/employees/edit.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = new Employee();
        employee.setLastName(req.getParameter("last-name"));
        employee.setFirstName(req.getParameter("first-name"));
        employee.setEmail(req.getParameter("email"));
        employee.setPassword(req.getParameter("password"));
        employee.setAddress(req.getParameter("adress"));
        employee.setPosition(req.getParameter("position"));
        employee.setType(EmployeeType.valueOf(req.getParameter("type")));

        if (Integer.parseInt(req.getParameter("id")) != -1) {
            employee.setId(Integer.parseInt(req.getParameter("id")));
        }

        Department department = DaoProvider.getDepartmentDao().findByName(req.getParameter("department"));
        if (department == null) {
            department = new Department(req.getParameter("department"));
            DaoProvider.getDepartmentDao().save(department);
        }

        employee.setDepartment(department);

        for (DayoffType type : DaoProvider.getDayoffTypeDao().getAll()) {
            Float nbDays = Float.parseFloat(req.getParameter("dayofftype-" + type.getId()));
            System.out.println("Type " + type.getName() + " : " + nbDays);
            //TODO
           /*if (type.getDefaultNbDays() != null .equals(nbDays)) {
                DayoffCount dayoffCount = new DayoffCount(nbDays, type, employee);
                DaoProvider.getDayoffCountDao().save(dayoffCount);
            }*/
        }

        System.out.println(employee);
    }
}