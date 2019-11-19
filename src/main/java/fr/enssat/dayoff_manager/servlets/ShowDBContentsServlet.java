package fr.enssat.dayoff_manager.servlets;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCount;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.department.Department;
import fr.enssat.dayoff_manager.db.employee.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "ShowDBContentsServlet",
        description = "Shows DB content",
        urlPatterns = {"/showDBContents"}
)
public class ShowDBContentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head><title>Hello World</title></head>");
        out.println("<body>");

        out.println("<hr><h1>Employees</h1>");
        for (Employee employee : DaoProvider.getEmployeeDao().getAll()) {
            out.println("<br><div>" + employee.toString() + "</div><br>");
        }

        out.println("<hr><h1>Departments</h1>");
        for (Department department : DaoProvider.getDepartmentDao().getAll()) {
            out.println("<br><div>" + department.toString() + "</div><br>");
        }

        out.println("<hr><h1>Dayoffs</h1>");
        for (Dayoff dayoff : DaoProvider.getDayoffDao().getAll()) {
            out.println("<br><div>" + dayoff.toString() + "</div><br>");
        }

        out.println("<hr><h1>DayoffCounts</h1>");
        for (DayoffCount dayoffCount : DaoProvider.getDayoffCountDao().getAll()) {
            out.println("<br><div>" + dayoffCount.toString() + "</div><br>");
        }

        out.println("<hr><h1>DayoffTypes</h1>");
        for (DayoffType dayoffType : DaoProvider.getDayoffTypeDao().getAll()) {
            out.println("<br><div>" + dayoffType.toString() + "</div><br>");
        }

        out.println("</body></html>");
    }
}
