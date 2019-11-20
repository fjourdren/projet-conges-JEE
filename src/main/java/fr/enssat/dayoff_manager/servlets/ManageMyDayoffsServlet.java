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
import java.util.Calendar;
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
        // check if user is connected
        HttpSession session = req.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null || !(employeeLogged.getType() == EmployeeType.RH_ADMIN || employeeLogged.getType() == EmployeeType.RH)) {
            resp.sendRedirect("default");
            return;
        }

        switch (req.getParameter("dayoff-action")) {
            case "edit":
                createAndSaveDayoffFromForm(req, employeeLogged);
                break;
            case "delete":
                deleteDayoffWithForm(req);
                break;
        }

        resp.sendRedirect("manage-my-dayoffs");
    }

    /**
     * Crée ou met à jour une demande de congé depuis un formulaire HTTP puis l'enregistre en base
     *
     * @param req      http request
     * @param employee employé
     */
    private void createAndSaveDayoffFromForm(HttpServletRequest req, Employee employee) {
        Dayoff dayoff;

        if (req.getParameter("dayoff-id") == null || req.getParameter("dayoff-id").isEmpty()) {
            dayoff = new Dayoff();
            dayoff.setDateCreation(new Date());
            dayoff.setStatus(DayoffStatus.WAITING);
        } else {
            dayoff = DaoProvider.getDayoffDao().findById(Long.parseLong(req.getParameter("dayoff-id")));
        }

        dayoff.setDateStart(convertHTMLDateToJavaDate(req.getParameter("start-date"), req.getParameter("start-date-type")));
        dayoff.setDateEnd(convertHTMLDateToJavaDate(req.getParameter("end-date"), req.getParameter("end-date-type")));
        dayoff.setCommentEmployee(req.getParameter("employee-comment"));
        dayoff.setType(DaoProvider.getDayoffTypeDao().findById(Long.parseLong(req.getParameter("dayoff-type"))));
        dayoff.setEmployee(employee);
        DaoProvider.getDayoffDao().save(dayoff);
    }

    /**
     * Supprime une demande de congé depuis un formulaire HTTP
     *
     * @param req http request
     */
    private void deleteDayoffWithForm(HttpServletRequest req) {
        Dayoff dayoff = DaoProvider.getDayoffDao().findById(Long.parseLong(req.getParameter("dayoff-id")));
        DaoProvider.getDayoffDao().delete(dayoff);
    }

    /**
     * Convertit une date HTML (yyyy-mm-yy) en java.util.Date en prenant en compte le type de date (MORNING|AFTERNOON)
     *
     * @param date     date HTML (yyyy-mm-yy)
     * @param dateType type de date (MORNING|AFTERNOON)
     * @return java.util.Date
     */
    private Date convertHTMLDateToJavaDate(String date, String dateType) {
        Calendar calendar = Calendar.getInstance();
        String[] dateParts = date.split("-");

        calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[2]));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        if (dateType.equals("AFTERNOON")) {
            calendar.set(Calendar.HOUR_OF_DAY, 12);
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        }

        return calendar.getTime();
    }
}
