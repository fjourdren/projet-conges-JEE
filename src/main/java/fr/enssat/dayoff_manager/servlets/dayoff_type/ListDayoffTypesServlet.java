package fr.enssat.dayoff_manager.servlets.dayoff_type;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffTypeDao;
import fr.enssat.dayoff_manager.servlets.EnhancedHttpServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet permettant d'afficher via une liste les types de congés
 * Il permet également d'un ajouter un nouveau et de modifier ou supprimer les existants
 * <p>
 * URLS:
 * - /dayofftype-list
 */
@WebServlet(
        name = "ListDayoffTypesServlet",
        description = "ListDayoffTypesServlet",
        urlPatterns = {"/dayofftype-list"}
)
public class ListDayoffTypesServlet extends EnhancedHttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Création du tableau contenant les colonnes du tableau
        List<String> datatableHeadArray = new ArrayList<>();
        datatableHeadArray.add("Nom");
        datatableHeadArray.add("Nb jours par défaut");
        datatableHeadArray.add("Modifier");
        datatableHeadArray.add("Supprimer");
        request.setAttribute("datatableHeadArray", datatableHeadArray);

        //Création du tableau contenant les données à afficher
        List<List<String>> datatableDataArray = new ArrayList<>();
        DayoffTypeDao dayoffTypeDao = DaoProvider.getDayoffTypeDao();

        for (DayoffType e : dayoffTypeDao.getAvailableDayoffTypes()) {
            List<String> lineToAdd = new ArrayList<>();

            lineToAdd.add(e.getName());
            if (e.getDefaultNbDays() == null) {
                lineToAdd.add("Illimité");
            } else {
                lineToAdd.add(e.getDefaultNbDays().toString());
            }

            lineToAdd.add("<a class=\"btn btn-primary\" href=\"dayofftype-add-edit?id=" + e.getId() + "\" role=\"button\">Modifier</a>"); // modify button
            lineToAdd.add("<a class=\"btn btn-danger\" href=\"dayofftype-delete?id=" + e.getId() + "\" role=\"button\">Supprimer</a>"); // delete button
            datatableDataArray.add(lineToAdd);
        }

        request.setAttribute("datatableDataArray", datatableDataArray);

        // page send
        request.setAttribute("leftMenuActive", "typesDayoff");
        request.setAttribute("componentNeeded", "dayoffTypeRender");

        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/template/index.jsp");

        dispatcher.forward(request, response);
    }
}
