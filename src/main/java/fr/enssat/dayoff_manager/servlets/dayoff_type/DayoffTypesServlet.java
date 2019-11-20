package fr.enssat.dayoff_manager.servlets.dayoff_type;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffTypeDao;
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
import java.util.ArrayList;

@WebServlet(
        name = "DayoffTypesServlet",
        description = "DayoffTypesServlet",
        urlPatterns = {"/congesTypes"}
)
public class DayoffTypesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = request.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null || employeeLogged.getType() != EmployeeType.RH_ADMIN) {
            response.sendRedirect("default");
            return;
        }

        // generate page
        // generate tabledata
        ArrayList<String> datatableHeadArray = new ArrayList<String>();

        datatableHeadArray.add("Nom");
        datatableHeadArray.add("Nb jours par défaut");

        datatableHeadArray.add("Modifier");
        datatableHeadArray.add("Supprimer");

        request.setAttribute("datatableHeadArray", datatableHeadArray);

        ArrayList<ArrayList<String>> datatableDataArray = new ArrayList<ArrayList<String>>();

        DayoffTypeDao dayoffTypeDao = DaoProvider.getDayoffTypeDao();

        //FIXME (Clément) On affiche seulement les DayoffTypes non marqués comme supprimés
        for (DayoffType e : dayoffTypeDao.getAvailableDayoffTypes()) {
            ArrayList<String> lineToAdd = new ArrayList<String>();

            lineToAdd.add(e.getName());
            if (e.getDefaultNbDays() == null) {
                lineToAdd.add("Illimité");
            } else {
                lineToAdd.add(e.getDefaultNbDays().toString());
            }

            lineToAdd.add("<a class=\"btn btn-primary\" href=\"congesTypes-add-edit?id=" + e.getId() + "\" role=\"button\">Modifier</a>"); // modify button
            lineToAdd.add("<a class=\"btn btn-danger\" href=\"congesTypes-delete?id=" + e.getId() + "\" role=\"button\">Supprimer</a>"); // delete button

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
