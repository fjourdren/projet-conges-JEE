package fr.enssat.dayoff_manager.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.department.Department;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeDao;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

/**
 * Servlet implementation class employeesServlet
 */
public class employeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public employeesServlet() {
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
		if(employeeLogged == null || employeeLogged.getType() != EmployeeType.RH_ADMIN) {
			response.sendRedirect("default");
			return;
		}
		
		// generate page
		RequestDispatcher dispatcher = request.getRequestDispatcher(
		          "/template/index.jsp");
		
		// generate tabledata
		ArrayList<String> datatableHeadArray = new ArrayList<String>();

		datatableHeadArray.add("Prénom");
		datatableHeadArray.add("Nom");
		datatableHeadArray.add("Adresse");
		datatableHeadArray.add("Poste");
		datatableHeadArray.add("Email");
		datatableHeadArray.add("Service");
		
		datatableHeadArray.add("Modifier");
		datatableHeadArray.add("Supprimer");
		
		request.setAttribute("datatableHeadArray", datatableHeadArray);
		
		ArrayList<ArrayList<String>> datatableDataArray = new ArrayList<ArrayList<String>>();
		
		EmployeeDao employeeDao = DaoProvider.getEmployeeDao();
		
		for(Employee e: employeeDao.getAll()) {
			ArrayList<String> lineToAdd = new ArrayList<String>();

			lineToAdd.add(e.getFirstName());
			lineToAdd.add(e.getLastName());
			lineToAdd.add(e.getAddress());
			lineToAdd.add(e.getPosition());
			lineToAdd.add(e.getEmail());
			lineToAdd.add(e.getDepartment().getName());
			lineToAdd.add(""); // modify button
			lineToAdd.add(""); // delete button
			
			datatableDataArray.add(lineToAdd);
		}
		
		
		request.setAttribute("datatableDataArray", datatableDataArray);
		
		// page send
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
