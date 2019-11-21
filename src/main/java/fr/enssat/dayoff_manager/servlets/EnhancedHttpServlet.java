package fr.enssat.dayoff_manager.servlets;

import fr.enssat.dayoff_manager.Constants;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public abstract class EnhancedHttpServlet extends HttpServlet {

    /**
     * Affiche au prochain refresh de la page le message donné en paramètre
     *
     * @param req     http request
     * @param resp    http response
     * @param type    type (danger,success)
     * @param message message
     */
    public void showFlashMessage(HttpServletRequest req, HttpServletResponse resp,
                                 String type, String message) throws IOException {
        req.getSession().setAttribute(Constants.FLASH_MSG_TYPE_SESSION_ATTRIBUTE_NAME, type);
        req.getSession().setAttribute(Constants.FLASH_MSG_CONTENT_SESSION_ATTRIBUTE_NAME, message);
    }

    /**
     * Retourne l'utilisateur actuellement connecté
     *
     * @param session session HTTP
     * @return utilisateur actuellement connecté
     */
    public Employee getLoggedUser(HttpSession session) {
        return (Employee) session.getAttribute(Constants.LOGGED_USER_SESSION_ATTRIBUTE_NAME);
    }

    /**
     * Modifie l'utilisateur actuellement connecté
     *
     * @param session    session HTTP
     * @param loggedUser utlisateur
     */
    public void setLoggedUser(HttpSession session, Employee loggedUser) {
        session.setAttribute(Constants.LOGGED_USER_SESSION_ATTRIBUTE_NAME, loggedUser);
    }



}
