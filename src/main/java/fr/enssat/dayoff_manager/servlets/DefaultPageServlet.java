package fr.enssat.dayoff_manager.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.employee.Employee;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;


@WebServlet(
        name = "DefaultPageServlet",
        description = "Default page",
        urlPatterns = {"/default"}
)
public class DefaultPageServlet extends EnhancedHttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Employee employeeLogged = (Employee) session.getAttribute("employeeLogged");

        List<Dayoff> dayoffEmployee = DaoProvider.getEmployeeDao().getDayOffs(employeeLogged);
        List<Dayoff> dayOffFuture = new ArrayList<>();

        Map<String, Integer> mapData = new HashMap<String, Integer>();

        String typeNom = new String("");

        Date currentDate = new Date();
        for(Dayoff dayoff : dayoffEmployee){
            typeNom = dayoff.getType().getName();
            if(dayoff.getDateStart().compareTo(currentDate) >= 0){
                dayOffFuture.add(dayoff);
            }
            if(mapData.containsKey(typeNom)){
                mapData.replace(typeNom, mapData.get(typeNom) + (int)dayoff.getNbDays());
            }else{
                mapData.put(typeNom, (int)dayoff.getNbDays());
            }
        }

        req.setAttribute("componentNeeded", "home");
        req.setAttribute("dayOffFuture", dayOffFuture);
        req.setAttribute("mapData", mapData);


        RequestDispatcher dispatcher = req.getRequestDispatcher("/template/index.jsp");
        req.setAttribute("componentNeeded", "default");
        dispatcher.forward(req, resp);
    }
}
