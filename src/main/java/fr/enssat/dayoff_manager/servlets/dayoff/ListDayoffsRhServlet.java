package fr.enssat.dayoff_manager.servlets.dayoff;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDao;
import fr.enssat.dayoff_manager.db.dayoff.DayoffStatus;
import fr.enssat.dayoff_manager.servlets.EnhancedHttpServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet permettant de lister les demande de congés non validés, pour validation ou non
 * <p>
 * URLS :
 * - /rh-dayoff-list?id=ID
 */
@WebServlet(
        name = "ListDayoffsRhServlet",
        description = "ListDayoffsRhServlet",
        urlPatterns = {"/rh-dayoff-list"}
)

public class ListDayoffsRhServlet extends EnhancedHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> datatableHeadArray = new ArrayList<>();
        datatableHeadArray.add("Employee");
        datatableHeadArray.add("Date demande");
        datatableHeadArray.add("Date début");
        datatableHeadArray.add("Date fin");
        datatableHeadArray.add("Commentaire");
        datatableHeadArray.add("Commentaire RH");
        datatableHeadArray.add("Statut");
        datatableHeadArray.add(" ");
        req.setAttribute("datatableHeadArray", datatableHeadArray);

        List<List<String>> datatableDataArray = new ArrayList<>();
        DayoffDao dayoffDao = DaoProvider.getDayoffDao();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //On affiche ceux non traités
        for (Dayoff e : dayoffDao.getDayOffStatus(DayoffStatus.WAITING)) {
            List<String> lineToAdd = new ArrayList<>();
            lineToAdd.add(e.getEmployee().getFirstName() + " " + e.getEmployee().getLastName());
            lineToAdd.add(dateFormat.format(e.getDateCreation()));
            lineToAdd.add(dateFormat.format(e.getDateStart()));
            lineToAdd.add(dateFormat.format(e.getDateEnd()));
            lineToAdd.add(e.getCommentEmployee() == null ? "" : e.getCommentEmployee());
            lineToAdd.add(e.getCommentRH() == null ? "" : e.getCommentRH());
            lineToAdd.add(e.getStatus().name());
            lineToAdd.add("<a class=\"btn btn-primary\" href=\"rh-dayoff-edit?id=" + e.getId() + "\" role=\"button\">Traiter</a>"); // modify button

            datatableDataArray.add(lineToAdd);
        }

        req.setAttribute("datatableDataArray", datatableDataArray);
        req.setAttribute("leftMenuActive", "dayoffsRH");
        req.setAttribute("componentNeeded", "dayoffRender");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/template/index.jsp");
        dispatcher.forward(req, resp);
    }
}
