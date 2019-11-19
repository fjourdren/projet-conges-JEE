package fr.enssat.dayoff_manager.servlets.employees;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeDao;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        name = "EmployeesServlet",
        description = "Manage employees",
        urlPatterns = {"/employees"}
)
public class EmployeesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // check if user is connected
        HttpSession session = request.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");
        if (employeeLogged == null || employeeLogged.getType() != EmployeeType.RH_ADMIN) {
            response.sendRedirect("default");
            return;
        }

        // generate page
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

        for (Employee e : employeeDao.getAll()) {
            ArrayList<String> lineToAdd = new ArrayList<String>();

			lineToAdd.add(e.getFirstName());
			lineToAdd.add(e.getLastName());
			lineToAdd.add(e.getAddress());
			lineToAdd.add(e.getPosition());
			lineToAdd.add(e.getEmail());
			lineToAdd.add(e.getDepartment().getName());
			lineToAdd.add("<a class=\"btn btn-primary\" href=\"employees-add-edit?id=" + e.getId() + "\" role=\"button\">Modifier</a>"); // modify button
			lineToAdd.add("<a class=\"btn btn-danger\" href=\"employees-delete?id=" + e.getId() + "\" role=\"button\">Supprimer</a>"); // delete button

			datatableDataArray.add(lineToAdd);
		}


		request.setAttribute("datatableDataArray", datatableDataArray);

		// page send
		request.setAttribute("leftMenuActive", "employees");
		request.setAttribute("componentNeeded", "employeesRender");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(
		          "/template/index.jsp");
		
		dispatcher.forward(request, response);
	}

}
