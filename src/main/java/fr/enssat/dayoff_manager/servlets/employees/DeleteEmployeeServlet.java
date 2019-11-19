package fr.enssat.dayoff_manager.servlets.employees;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeDao;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

/**
 * Servlet implementation class DeleteEmployeeServlet
 */
public class DeleteEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteEmployeeServlet() {
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
		
		// delete
		if(request.getParameter("id") != null) {
			EmployeeDao employeeDao = DaoProvider.getEmployeeDao();
			
			Employee employee = null;
			
			try {
				employee = employeeDao.findById(Integer.parseInt(request.getParameter("id")));
			} catch(Error e) {
				session.setAttribute("flashType", "danger");
        		session.setAttribute("flashMessage", "Erreur de conversion");
        		
        		response.sendRedirect("employees");
        		
                return;
			}
			
			
			if(employee != null) {
				employeeDao.delete(employee);
				
				session.setAttribute("flashType", "success");
        		session.setAttribute("flashMessage", "Employé supprimé");
        		
        		response.sendRedirect("employees");
        		
                return;
			} else {
				session.setAttribute("flashType", "danger");
        		session.setAttribute("flashMessage", "Employé inconnu");
        		
        		response.sendRedirect("employees");
        		
                return;
			}
		} else {
			session.setAttribute("flashType", "danger");
    		session.setAttribute("flashMessage", "L'employé est non défini");
    		
    		response.sendRedirect("employees");
    		
            return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}