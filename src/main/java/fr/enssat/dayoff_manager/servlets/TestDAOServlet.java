package fr.enssat.dayoff_manager.servlets;

import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDaoMockImpl;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeDaoMockImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TestDAOServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Hello World</title></head>");
        out.println("<body>");
        out.println("<h1>Test (from servlet) !</h1>");

        //test pour le mock des Dayoff
        DayoffDaoMockImpl tdo = new DayoffDaoMockImpl();

        List<Dayoff> listDayoff = tdo.getAll();
        for (Dayoff d : listDayoff){
            out.println("id : " + d.getId() + " nb day : " + d.getNbDays());
        }
        Dayoff dayId0 = tdo.findById(0);

        // WARNING, probablement un problème avec les id hibernate et les dayoff du mock qui ne sont pas dans la db
        if(dayId0 != null){
            out.println("\nfind by id where id = 0 :" + dayId0.getId()+"<br><br>");
        }
        else{
            out.println("dayId0 is null<br><br>");
        }

        tdo.validate("You deserved it", listDayoff.get(0));
        out.println("\ncommentRH : " + listDayoff.get(0).getCommentRH() + "\nnb day : " + listDayoff.get(0).getNbDays());


        // test pour le mock des Employee
        EmployeeDaoMockImpl edo = new EmployeeDaoMockImpl();
        List<Employee> listEmployee = edo.getAll();
        for (Employee e : listEmployee){
            out.println(e.toString() + "\n<br>");

        }
        out.println("<br>");
        out.println("<br>");
        out.println("<br>login avec bon mot de passe : " + edo.login("fj@", "xXdidocheGamingXx"));
        out.println("<br>login avec mauvais mot de passe : " + edo.login("fj@", "123"));

        out.println("</body></html>");

    }

}
