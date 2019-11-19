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
            dayoffType.setId(-1);
        } else {
            //Modification employé existant
            int dayoffTypeID = -1;

            try {
                dayoffTypeID = Integer.parseInt(req.getParameter("id"));
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
        DayoffType dayoffType = new DayoffType();
        dayoffType.setName(req.getParameter("last-name"));
        try {
            dayoffType.setDefaultNbDays(Float.parseFloat(req.getParameter("first-name")));
        } catch (Exception e) {
            session.setAttribute("flashType", "danger");
            session.setAttribute("flashMessage", "Requête invalide");

            resp.sendRedirect("conges");

            return;
        }


        if (Integer.parseInt(req.getParameter("id")) != -1) {
            dayoffType.setId(Integer.parseInt(req.getParameter("id")));
        }

        // save
        DayoffTypeDao dayoffTypeDao = DaoProvider.getDayoffTypeDao();

        dayoffTypeDao.save(dayoffType);

        if (dayoffTypeDao.findById(dayoffType.getId()) == null) {
            session.setAttribute("flashType", "success");
            session.setAttribute("flashMessage", "Type de congés ajouté");
        } else {
            session.setAttribute("flashType", "success");
            session.setAttribute("flashMessage", "Type de congés modifié");
        }

        resp.sendRedirect("congesTypes");

    }
}