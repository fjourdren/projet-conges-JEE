package fr.enssat.dayoff_manager.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.enssat.dayoff_manager.db.employee.Employee;

/**
 * Servlet implementation class defaultPage
 */
public class defaultPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public defaultPage() {
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
		if(employeeLogged == null) {
			response.sendRedirect("login");
			return;
		}
		
		// generate page
		RequestDispatcher dispatcher = request.getRequestDispatcher(
		          "/template/index.jsp");
		
		ArrayList<String> datatableHeadArray = new ArrayList<String>();
		datatableHeadArray.add("collumn 1");
		datatableHeadArray.add("collumn 2");
		request.setAttribute("datatableHeadArray", datatableHeadArray);
		
		ArrayList<ArrayList<String>> datatableDataArray = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> line1 = new ArrayList<String>();
		line1.add("content");
		line1.add("content2");
		
		datatableDataArray.add(line1);
		request.setAttribute("datatableDataArray", datatableDataArray);
		
		request.setAttribute("componentNeeded", "employees");
		
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
