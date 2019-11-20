package fr.enssat.dayoff_manager.servlets.dayoff;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDao;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Servlet implementation class employeesServlet
 */

@WebServlet(
        name = "DayoffServlet",
        description = "DayoffServlet",
        urlPatterns = {"/dayoffsRH"}
)
public class DayoffServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DayoffServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = request.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null || employeeLogged.getType() != EmployeeType.RH) {
            response.sendRedirect("default");
            return;
        }

        // generate page
        // generate tabledata
        ArrayList<String> datatableHeadArray = new ArrayList<String>();

        datatableHeadArray.add("Employee");
        datatableHeadArray.add("Date demande");
        datatableHeadArray.add("Date début");
        datatableHeadArray.add("Date fin");
        datatableHeadArray.add("Commentaire");
        datatableHeadArray.add("Commentaire RH");
        datatableHeadArray.add("Statut");

        request.setAttribute("datatableHeadArray", datatableHeadArray);

        ArrayList<ArrayList<String>> datatableDataArray = new ArrayList<ArrayList<String>>();

        DayoffDao dayoffDao = DaoProvider.getDayoffDao();

        for (Dayoff e : dayoffDao.getAll()) {
            ArrayList<String> lineToAdd = new ArrayList<String>();

            lineToAdd.add(e.getEmployee().getFirstName()+" "+e.getEmployee().getLastName());

            //on transforme les date en String

            Date dateCreation = e.getDateCreation();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            String creaDate = dateFormat.format(dateCreation);

            Date dateDebut = e.getDateCreation();
            String debDate = dateFormat.format(dateDebut);

            Date dateFin = e.getDateCreation();
            String finDate = dateFormat.format(dateFin);


            lineToAdd.add(creaDate);
            lineToAdd.add(debDate);
            lineToAdd.add(finDate);
            lineToAdd.add(e.getCommentEmployee());
            lineToAdd.add(e.getCommentRH());
            lineToAdd.add(e.getStatus().name());


            datatableDataArray.add(lineToAdd);
        }


        request.setAttribute("datatableDataArray", datatableDataArray);

        // page send
        request.setAttribute("leftMenuActive", "dayoffsRH");
        request.setAttribute("componentNeeded", "dayoffRender");

        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/template/index.jsp");

        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
