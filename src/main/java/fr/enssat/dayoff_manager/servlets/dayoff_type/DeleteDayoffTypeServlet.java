package fr.enssat.dayoff_manager.servlets.dayoff_type;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffTypeDao;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        name = "DeleteDayoffTypeServlet",
        description = "DeleteDayoffTypeServlet",
        urlPatterns = {"/congesTypes-delete"}
)
public class DeleteDayoffTypeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = request.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null || employeeLogged.getType() != EmployeeType.RH_ADMIN) {
            response.sendRedirect("default");
            return;
        }

        // delete
        if (request.getParameter("id") != null) {
            DayoffTypeDao dayoffTypeDao = DaoProvider.getDayoffTypeDao();
            DayoffType dayoffType = null;

            try {
                dayoffType = dayoffTypeDao.findById(Long.parseLong(request.getParameter("id")));
            } catch (Error e) {
                session.setAttribute("flashType", "danger");
                session.setAttribute("flashMessage", "Erreur de conversion");
                response.sendRedirect("congesTypes");
                return;
            }

            if (dayoffType != null) {
                //FIXME (Clément) Un DayoffType ne PEUT PAS être supprimé, il peut seulement être marqué comme supprimé
                dayoffType.setDeleted(true);
                dayoffTypeDao.save(dayoffType);

                session.setAttribute("flashType", "success");
                session.setAttribute("flashMessage", "Type de congés supprimé");
                response.sendRedirect("congesTypes");
            } else {
                session.setAttribute("flashType", "danger");
                session.setAttribute("flashMessage", "Type de congés inconnu");
                response.sendRedirect("congesTypes");
            }
        } else {
            session.setAttribute("flashType", "danger");
            session.setAttribute("flashMessage", "Paramètre manquant");
            response.sendRedirect("congesTypes");
        }
    }
}
