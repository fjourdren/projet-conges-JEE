package fr.enssat.dayoff_manager.servlets.dayoff_type;

import fr.enssat.dayoff_manager.db.DaoProvider;
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
        name = "EditDayoffTypeServlet",
        description = "EditDayoffTypeServlet",
        urlPatterns = {"/congesTypes-add-edit"}
)
public class EditDayoffTypeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = req.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null || employeeLogged.getType() != EmployeeType.RH_ADMIN) {
            resp.sendRedirect("default");
            return;
        }

        // generate
        DayoffType dayoffType = new DayoffType();

        if (req.getParameter("id") == null) {
            // if new DayoffType
            //FIXME (Clément) NE PAS METTRE l'ID à -1 (fause bonne idée de ma part)
            //dayoffType.setId(-1L);
        } else {
            //Modification employé existant
            Long dayoffTypeID;

            try {
                dayoffTypeID = Long.parseLong(req.getParameter("id"));
                if (dayoffTypeID < 0) throw new IllegalArgumentException("dayoffTypeID");
            } catch (Exception e) {
                session.setAttribute("flashType", "danger");
                session.setAttribute("flashMessage", "Requête invalide");

                resp.sendRedirect("congesTypes");

                return;
            }

            dayoffType = DaoProvider.getDayoffTypeDao().findById(dayoffTypeID);
            if (dayoffType == null) {
                session.setAttribute("flashType", "danger");
                session.setAttribute("flashMessage", "Type de congés inconnu");

                resp.sendRedirect("congesTypes");

                return;
            }
        }

        req.setAttribute("dayoffType", dayoffType);

        req.setAttribute("componentNeeded", "dayoffTypeEditAdd");

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/template/index.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = req.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null || employeeLogged.getType() != EmployeeType.RH_ADMIN) {
            resp.sendRedirect("default");
            return;
        }

        // process
        //FIXME (Clément) Lors de la modification, il faut récupérer l'objet existant depuis la DB (sinon bug)
        DayoffType dayoffType;
        if (req.getParameter("id") == null) {
            dayoffType = new DayoffType();
        } else {
            dayoffType = DaoProvider.getDayoffTypeDao().findById(Long.parseLong(req.getParameter("id")));
        }

        dayoffType.setName(req.getParameter("last-name"));
        try {
            dayoffType.setDefaultNbDays(Float.parseFloat(req.getParameter("first-name")));
        } catch (Exception e) {
            session.setAttribute("flashType", "danger");
            session.setAttribute("flashMessage", "Requête invalide");
            resp.sendRedirect("conges");
            return;
        }

        // save
        //FIXME (Clément) Si la sauvegarde échoue, une exception est levée
        try {
            DaoProvider.getDayoffTypeDao().save(dayoffType);
            session.setAttribute("flashType", "success");
            session.setAttribute("flashMessage", "Type de congés ajouté");
        } catch (Exception e) {
            session.setAttribute("flashType", "success");
            session.setAttribute("flashMessage", "Type de congés modifié");
        }

        resp.sendRedirect("congesTypes");
    }
}
