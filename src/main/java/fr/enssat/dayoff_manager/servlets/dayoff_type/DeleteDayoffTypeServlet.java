package fr.enssat.dayoff_manager.servlets.dayoff_type;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffTypeDao;
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
 * Servlet permettant de supprimer un type de congés
 * <p>
 * URLS:
 * - /dayofftype-delete?id=ID
 */
@WebServlet(
        name = "DeleteDayoffTypeServlet",
        description = "DeleteDayoffTypeServlet",
        urlPatterns = {"/dayofftype-delete"}
)
public class DeleteDayoffTypeServlet extends EnhancedHttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // delete
        if (request.getParameter("id") != null) {
            DayoffTypeDao dayoffTypeDao = DaoProvider.getDayoffTypeDao();
            DayoffType dayoffType = null;

            try {
                dayoffType = dayoffTypeDao.findById(Long.parseLong(request.getParameter("id")));
            } catch (Exception e) {
                showFlashMessage(request, response, "danger", "Requête invalide");
                response.sendRedirect("dayofftype-list");
                return;
            }

            if (dayoffType != null) {
                //Un DayoffType ne PEUT PAS être supprimé, il peut seulement être marqué comme supprimé
                dayoffType.setDeleted(true);

                try {
                    dayoffTypeDao.save(dayoffType);
                    showFlashMessage(request, response, "success", "Type de congé supprimé");
                } catch (Exception e) {
                    e.printStackTrace();
                    showFlashMessage(request, response, "danger", "Erreur pendant enregistrement");
                    response.sendRedirect("dayofftype-list");
                }

                response.sendRedirect("dayofftype-list");
            } else {
                showFlashMessage(request, response, "danger", "Type de congés inexistant");
                response.sendRedirect("dayofftype-list");
            }
        } else {
            showFlashMessage(request, response, "danger", "Requête invalide");
            response.sendRedirect("dayofftype-list");
        }
    }
}
