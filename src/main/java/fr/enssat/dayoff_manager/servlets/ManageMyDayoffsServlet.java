package fr.enssat.dayoff_manager.servlets;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffStatus;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
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
import java.util.Date;
import java.util.List;

@WebServlet(
        name = "ManageMyDayoffsServlet",
        description = "ManageMyDayoffsServlet",
        urlPatterns = {"/manage-my-dayoffs"}
)
public class ManageMyDayoffsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = req.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null || !(employeeLogged.getType() == EmployeeType.RH_ADMIN || employeeLogged.getType() == EmployeeType.RH)) {
            resp.sendRedirect("default");
            return;
        }

        List<Dayoff> dayoffs = DaoProvider.getEmployeeDao().getDayOffs(employeeLogged);
        List<DayoffType> dayoffTypes = DaoProvider.getDayoffTypeDao().getAll();

        req.setAttribute("componentNeeded", "manageMyDayoffs");
        req.setAttribute("dayoffs", dayoffs);
        req.setAttribute("dayoffTypes", dayoffTypes);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/template/index.jsp");
        dispatcher.forward(req, resp);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Date startDate = new Date();
        String[] startDateParts = req.getParameter("start-date").split("-");
        startDate.setYear(Integer.parseInt(startDateParts[0]));
        startDate.setMonth(Integer.parseInt(startDateParts[1]));
        startDate.setDate(Integer.parseInt(startDateParts[2]));
        if (req.getParameter("start-date-type").equals("AFTERNOON")) {
            startDate.setHours(12);
        }

        Date endDate = new Date();
        String[] endDateParts = req.getParameter("end-date").split("-");
        endDate.setYear(Integer.parseInt(endDateParts[0]));
        endDate.setMonth(Integer.parseInt(endDateParts[1]));
        endDate.setDate(Integer.parseInt(endDateParts[2]));
        if (req.getParameter("end-date-type").equals("AFTERNOON")) {
            endDate.setHours(12);
        }

        Dayoff dayoff = new Dayoff();
        dayoff.setStatus(DayoffStatus.WAITING);
        dayoff.setDateStart(startDate);
        dayoff.setDateEnd(endDate);
        dayoff.setCommentRH(null);
        dayoff.setCommentEmployee(req.getParameter("employee-comment"));


        String employeeComment = req.getParameter("employee-comment");
        String typeID = req.getParameter("dayoff-type");

        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(employeeComment);
        System.out.println(typeID);

    }
}
