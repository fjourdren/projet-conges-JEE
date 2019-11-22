package fr.enssat.dayoff_manager.servlets.dayoff;

import fr.enssat.dayoff_manager.DayoffUtils;
import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffStatus;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCount;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.servlets.EnhancedHttpServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet(
        name = "ManageMyDayoffsServlet",
        description = "ManageMyDayoffsServlet",
        urlPatterns = {"/manage-my-dayoffs"}
)
public class ManageMyDayoffsServlet extends EnhancedHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee user = getLoggedUser(req.getSession());
        List<Dayoff> dayoffs = DaoProvider.getEmployeeDao().getDayOffs(user);
        List<DayoffType> dayoffTypes = DaoProvider.getDayoffTypeDao().getAll();

        req.setAttribute("componentNeeded", "manageMyDayoffs");
        req.setAttribute("dayoffs", dayoffs);
        req.setAttribute("dayoffTypes", dayoffTypes);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/template/index.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee user = getLoggedUser(req.getSession());

        switch (req.getParameter("dayoff-action")) {
            case "edit":
                addOrEditDayoffWithForm(req, resp);
                break;
            case "delete":
                deleteDayoffWithForm(req, resp);
                break;
            case "nothing":
                //nothing
                break;
            default:
                resp.sendRedirect("default");
                return;
        }

        resp.sendRedirect("manage-my-dayoffs");
    }

    private void addOrEditDayoffWithForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee user = getLoggedUser(req.getSession());
        Dayoff newOrUpdatedDayOff = createDayoffFromForm(req, user);
        Dayoff oldDayOff = null;

        if (newOrUpdatedDayOff.getId() != null) {
            oldDayOff = DaoProvider.getDayoffDao().findById(newOrUpdatedDayOff.getId());
        }

        // Vérification de base de la demande de congés
        String validationError = DayoffUtils.isDayOffValid(newOrUpdatedDayOff);
        if (validationError != null) {
            showFlashMessage(req, resp, "danger", validationError);
            return;
        }

        // Calcul nombre de jours disponibles pour l'employé et le type de congé
        Float remainingDaysForDayoffType = DaoProvider.getEmployeeDao().nbDaysUsable(user, newOrUpdatedDayOff.getType());
        if (remainingDaysForDayoffType != null) {
            if (oldDayOff != null && oldDayOff.getType().equals(newOrUpdatedDayOff.getType())) {
                remainingDaysForDayoffType += oldDayOff.getNbDays();
            }

            // Vérification que le nombre de jours disponible est suffisant
            if (remainingDaysForDayoffType < newOrUpdatedDayOff.getNbDays()) {
                showFlashMessage(req, resp, "danger", "Vous n'avez pas assez de jours de congés disponibles");
                return;
            }
        }

        try {
            updateRemainingDays(oldDayOff, newOrUpdatedDayOff, user);
            DaoProvider.getDayoffDao().save(newOrUpdatedDayOff);
            showFlashMessage(req, resp, "success", "Demande de congés enregistré");
        } catch (Exception e) {
            e.printStackTrace();
            showFlashMessage(req, resp, "danger", "Erreur enregistrement");
        }
    }

    /**
     * Supprime une demande de congé depuis un formulaire HTTP
     *
     * @param req http request
     */
    private void deleteDayoffWithForm(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Employee user = getLoggedUser(req.getSession());
        Dayoff dayoff = DaoProvider.getDayoffDao().findById(Long.parseLong(req.getParameter("dayoff-id")));
        if (dayoff.getStatus() != DayoffStatus.WAITING) {
            showFlashMessage(req, resp, "danger", "Action impossible");
            return;
        }

        try {
            updateRemainingDays(dayoff, null, user);
            DaoProvider.getDayoffDao().delete(dayoff);
        } catch (Exception e) {
            e.printStackTrace();
            showFlashMessage(req, resp, "danger", "Erreur suppression");
        }
    }

    private void updateRemainingDays(Dayoff oldDayoff, Dayoff newOrUpdatedDayOff, Employee employee) {
        DayoffCount count = null;

        if (oldDayoff != null) {
            count = DaoProvider.getDayoffCountDao().findByEmployeeAndDayoffType(employee, oldDayoff.getType());
            if (count.getNbDays() != null) {
                count.setNbDays(count.getNbDays() + oldDayoff.getNbDays());
                DaoProvider.getDayoffCountDao().save(count);
            }
        }

        if (newOrUpdatedDayOff != null) {
            count = DaoProvider.getDayoffCountDao().findByEmployeeAndDayoffType(employee, newOrUpdatedDayOff.getType());
            if (count.getNbDays() != null) {
                count.setNbDays(count.getNbDays() - newOrUpdatedDayOff.getNbDays());
                DaoProvider.getDayoffCountDao().save(count);
            }
        }
    }

    /**
     * Crée ou met à jour une demande de congé depuis un formulaire HTTP
     *
     * @param req      http request
     * @param employee employé
     */
    private Dayoff createDayoffFromForm(HttpServletRequest req, Employee employee) {
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
        dayoff.setNbDays(DayoffUtils.calcNbDays(dayoff));
        return dayoff;
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
