package fr.enssat.dayoff_manager.servlets.dayoff;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffStatus;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCount;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;
import fr.enssat.dayoff_manager.servlets.EnhancedHttpServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet permettant de valider ou non une demande de congés
 * <p>
 * URLS :
 * - /rh-dayoff-edit?id=ID
 */
@WebServlet(
        name = "RhDayoffEditServlet",
        description = "RhDayoffEditServlet",
        urlPatterns = {"/rh-dayoff-edit"}
)
public class RhDayoffEditServlet extends EnhancedHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") == null) {
            resp.sendRedirect("rh-dayoff-list");
            return;
        }

        Long dayoffID;
        try {
            dayoffID = Long.parseLong(req.getParameter("id"));
            if (dayoffID < 0) throw new IllegalArgumentException("dayoffID");
        } catch (Exception e) {
            showFlashMessage(req, resp, "danger", "Requête invalide");
            resp.sendRedirect("rh-dayoff-list");
            return;
        }

        Dayoff dayoff = DaoProvider.getDayoffDao().findById(dayoffID);
        if (dayoff == null) {
            showFlashMessage(req, resp, "danger", "Demande non trouvé");
            resp.sendRedirect("rh-dayoff-list");
            return;
        }

        req.setAttribute("dayoff", dayoff);
        req.setAttribute("componentNeeded", "dayoffTraiterDemande");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/template/index.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Dayoff dayoff = DaoProvider.getDayoffDao().findById(Long.parseLong(req.getParameter("id")));
        DayoffStatus status = DayoffStatus.valueOf(req.getParameter("choix-rh"));
        Employee currentUser = getLoggedUser(req.getSession());

        if (dayoff.getEmployee().getType() == EmployeeType.RH_ADMIN && currentUser.getType() != EmployeeType.RH_ADMIN) {
            showFlashMessage(req, resp, "danger", "Une demande réalisé par un manager RH ne peut être validé que par un manager RH");
            resp.sendRedirect("rh-dayoff-list");
            return;
        }

        if (dayoff.getEmployee().getType() == EmployeeType.RH && currentUser.equals(dayoff.getEmployee())) {
            showFlashMessage(req, resp, "danger", "Vous ne pouvez pas valider vous-même vos demandes de congé");
            resp.sendRedirect("rh-dayoff-list");
            return;
        }

        try {
            switch (status) {
                case ACCEPTED: {
                    DaoProvider.getDayoffDao().validate(dayoff, req.getParameter("comment-rh"));
                    break;
                }
                case REFUSED: {
                    if (req.getParameter("comment-rh").isEmpty()) {
                        showFlashMessage(req, resp, "danger", "Vous devez entrer une raison de refus");
                        resp.sendRedirect("rh-dayoff-edit?id=" + dayoff.getId());
                        return;
                    }

                    //rollback nbDaysUsable of employee
                    DayoffCount count = DaoProvider.getDayoffCountDao().findByEmployeeAndDayoffType(dayoff.getEmployee(), dayoff.getType());
                    if (count != null && count.getNbDays() != null) {
                        count.setNbDays(count.getNbDays() + dayoff.getNbDays());
                        DaoProvider.getDayoffCountDao().save(count);
                    }

                    DaoProvider.getDayoffDao().refuse(dayoff, req.getParameter("comment-rh"));
                    break;
                }
                case WAITING:
                    dayoff.setCommentRH(req.getParameter("comment-rh"));
                    DaoProvider.getDayoffDao().save(dayoff);
                    break;
            }
            resp.sendRedirect("rh-dayoff-list");
        } catch (Exception e) {
            e.printStackTrace();
            showFlashMessage(req, resp, "danger", "Erreur enregistrement");
            resp.sendRedirect("rh-dayoff-list");
        }
    }
}
