package fr.enssat.dayoff_manager.servlets.employees;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeDao;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;
import fr.enssat.dayoff_manager.servlets.EnhancedHttpServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Servlet permettant d'afficher via une liste les employés
 * Il permet également d'un ajouter un nouveau et de modifier ou supprimer les existants
 * <p>
 * URLS:
 * - /employees-list
 */
@WebServlet(
        name = "EmployeesServlet",
        description = "Manage employees",
        urlPatterns = {"/employees-list"}
)
public class ListEmployeesServlet extends EnhancedHttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Création du tableau contenant les colonnes du tableau
        List<String> datatableHeadArray = new ArrayList<>();
        datatableHeadArray.add("Prénom");
        datatableHeadArray.add("Nom");
        //datatableHeadArray.add("Adresse");
        datatableHeadArray.add("Poste");
        datatableHeadArray.add("Email");
        datatableHeadArray.add("Service");
        datatableHeadArray.add("Modifier");
        datatableHeadArray.add("Supprimer");
        request.setAttribute("datatableHeadArray", datatableHeadArray);

        //Création du tableau contenant les données à afficher
        List<List<String>> datatableDataArray = new ArrayList<>();
        EmployeeDao employeeDao = DaoProvider.getEmployeeDao();

        for (Employee e : employeeDao.getAll()) {
            List<String> lineToAdd = new ArrayList<>();

            lineToAdd.add(e.getFirstName());
            lineToAdd.add(e.getLastName());
            //lineToAdd.add(e.getAddress());
            lineToAdd.add(e.getPosition());
            lineToAdd.add(e.getEmail());
            lineToAdd.add(e.getDepartment().getName());
            lineToAdd.add("<a class=\"btn btn-primary\" href=\"employees-add-edit?id=" + e.getId() + "\" role=\"button\">Modifier</a>"); // modify button
            lineToAdd.add("<a class=\"btn btn-danger\" href=\"employees-delete?id=" + e.getId() + "\" role=\"button\">Supprimer</a>"); // delete button

            datatableDataArray.add(lineToAdd);
        }


        request.setAttribute("datatableDataArray", datatableDataArray);
        request.setAttribute("leftMenuActive", "employees");
        request.setAttribute("componentNeeded", "employeesRender");

        RequestDispatcher dispatcher = request.getRequestDispatcher(
                "/template/index.jsp");

        dispatcher.forward(request, response);
    }

}
