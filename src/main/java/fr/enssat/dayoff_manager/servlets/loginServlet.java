package fr.enssat.dayoff_manager.servlets;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeDao;
import fr.enssat.dayoff_manager.db.employee.EmployeeDaoMockImpl;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class loginServlet
 */
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
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
		if(employeeLogged != null) {
			response.sendRedirect("default");
			return;
		}
				
		// login page
		RequestDispatcher dispatcher = request.getRequestDispatcher(
		          "/template/index.jsp");
		
		request.setAttribute("componentNeeded", "login");
		
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		EmployeeDao employeeDao = DaoProvider.getEmployeeDao();
		
		Employee employeeLogin = employeeDao.login(email, password);
		
		HttpSession session = request.getSession();

		if(employeeLogin != null) {
			
			session.setAttribute("employeeLogged", employeeLogin);
			
			response.sendRedirect("default");
		} else {
			session.setAttribute("flashType", "danger");
			session.setAttribute("flashMessage", "Authentification invalide.");
			
			response.sendRedirect("login");
		}
		
	}

}
