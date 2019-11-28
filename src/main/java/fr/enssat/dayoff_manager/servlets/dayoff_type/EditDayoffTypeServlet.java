package fr.enssat.dayoff_manager.servlets.dayoff_type;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.servlets.EnhancedHttpServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet permettant d'ajouter ou de modifier un type de congés
 * <p>
 * URLS:
 * - /dayofftype-add-edit
 * - /dayofftype-add-edit?id=ID
 */
@WebServlet(
        name = "EditDayoffTypeServlet",
        description = "EditDayoffTypeServlet",
        urlPatterns = {"/dayofftype-add-edit"}
)
public class EditDayoffTypeServlet extends EnhancedHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DayoffType dayoffType;

        if (req.getParameter("id") != null) {
            //Modification d'un type de congés existant
            Long dayoffTypeID;

            try {
                dayoffTypeID = Long.parseLong(req.getParameter("id"));
                if (dayoffTypeID < 0) throw new IllegalArgumentException("dayoffTypeID");
            } catch (Exception e) {
                showFlashMessage(req, resp, "danger", "Requête invalide");
                resp.sendRedirect("dayofftype-list");
                return;
            }

            dayoffType = DaoProvider.getDayoffTypeDao().findById(dayoffTypeID);
            if (dayoffType == null) {
                showFlashMessage(req, resp, "danger", "Type de congés inconnu");
                resp.sendRedirect("dayofftype-list");
                return;
            }
        } else {
            //Création d'un type de congés
            dayoffType = null;
        }

        req.setAttribute("dayoffType", dayoffType);
        req.setAttribute("componentNeeded", "dayoffTypeEditAdd");

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/template/index.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DayoffType dayoffType;
        if (req.getParameter("id") == null || req.getParameter("id").isEmpty()) {
            dayoffType = new DayoffType();
        } else {
            dayoffType = DaoProvider.getDayoffTypeDao().findById(Long.parseLong(req.getParameter("id")));
        }

        dayoffType.setName(req.getParameter("name"));
        try {
            if (req.getParameter("nb-days-unlimited") != null) {
                dayoffType.setDefaultNbDays(null);
            } else {
                dayoffType.setDefaultNbDays(Float.parseFloat(req.getParameter("nb-days")));
            }
        } catch (Exception e) {
            showFlashMessage(req, resp, "danger", "Nombre de jours non valide. Doit être un multiple de 0.5 et être positif");
            resp.sendRedirect("dayofftype-list");
            return;
        }

        // Sauvegarde de l'objet
        try {
            DaoProvider.getDayoffTypeDao().save(dayoffType);
            showFlashMessage(req, resp, "success", "Type de congés sauvegardé");
        } catch (Exception e) {
            showFlashMessage(req, resp, "success", "Erreur pendant sauvegarde type de congés");
        }

        resp.sendRedirect("dayofftype-list");
    }
}
