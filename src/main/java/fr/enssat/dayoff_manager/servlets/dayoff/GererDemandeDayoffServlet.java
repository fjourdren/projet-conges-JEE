package fr.enssat.dayoff_manager.servlets.dayoff;

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

@WebServlet(
        name = "GererDemandeDayoffServlet",
        description = "GererDemandeDayoffServlet",
        urlPatterns = {"/dayoffsRH-demande"}
)
public class GererDemandeDayoffServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = req.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null || employeeLogged.getType() != EmployeeType.RH) {
            resp.sendRedirect("default");
            return;
        }

        // generate
        Dayoff dayoff = new Dayoff();

        if (req.getParameter("id") == null) {
            // if new DayoffType
            //FIXME (Clément) NE PAS METTRE l'ID à -1 (fause bonne idée de ma part)
            //dayoffType.setId(-1L);
        } else {
            //Modification employé existant
            Long dayoffID;

            try {
                dayoffID = Long.parseLong(req.getParameter("id"));
                if (dayoffID < 0) throw new IllegalArgumentException("dayoffID");
            } catch (Exception e) {
                session.setAttribute("flashType", "danger");
                session.setAttribute("flashMessage", "Requête invalide");

                resp.sendRedirect("dayoffsRH");

                return;
            }

            dayoff = DaoProvider.getDayoffDao().findById(dayoffID);
            if (dayoff == null) {
                session.setAttribute("flashType", "danger");
                session.setAttribute("flashMessage", "Demande non trouvé");

                resp.sendRedirect("dayoffsRH");

                return;
            }
        }

        req.setAttribute("dayoff", dayoff);

        req.setAttribute("componentNeeded", "dayoffTraiterDemande");

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/template/index.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = req.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null || employeeLogged.getType() != EmployeeType.RH) {
            resp.sendRedirect("default");
            return;
        }

        // process
        //FIXME (Clément) Lors de la modification, il faut récupérer l'objet existant depuis la DB (sinon bug)
        Dayoff dayoff;
        if (req.getParameter("id") == null) {
            dayoff = new Dayoff();
        } else {
            dayoff = DaoProvider.getDayoffDao().findById(Long.parseLong(req.getParameter("id")));
        }

        dayoff.setCommentRH(req.getParameter("comment-rh"));
        DayoffStatus status = DayoffStatus.valueOf(req.getParameter("choix-rh"));
        if(status == DayoffStatus.ACCEPTED){
            DaoProvider.getDayoffDao().validate(dayoff,req.getParameter("comment-rh"));
        }else if(status == DayoffStatus.REFUSED){
            if(req.getParameter("comment-rh") == ""){
                session.setAttribute("flashType", "danger");
                session.setAttribute("flashMessage", "Vous devez entrer une raison de refus");
                resp.sendRedirect("dayoffsRH-demande?id="+dayoff.getId());

                return;
            }else{
                DaoProvider.getDayoffDao().refuse(dayoff,req.getParameter("comment-rh"));
            }
        }
        //dayoff.setStatus(status);

        // save
        //FIXME (Clément) Si la sauvegarde échoue, une exception est levée
        try {
            DaoProvider.getDayoffDao().save(dayoff);
            session.setAttribute("flashType", "success");
            session.setAttribute("flashMessage", "Demande traité");
        } catch (Exception e) {
            session.setAttribute("flashType", "success");
            session.setAttribute("flashMessage", "Demande traité");
        }

        resp.sendRedirect("dayoffsRH");
    }
}
