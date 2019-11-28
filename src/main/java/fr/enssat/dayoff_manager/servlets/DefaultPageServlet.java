package fr.enssat.dayoff_manager.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "DefaultPageServlet",
        description = "Default page",
        urlPatterns = {"/default"}
)
public class DefaultPageServlet extends EnhancedHttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/template/index.jsp");
        request.setAttribute("componentNeeded", "default");
        dispatcher.forward(request, response);
    }
}
