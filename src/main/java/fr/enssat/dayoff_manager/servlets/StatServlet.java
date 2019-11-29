package fr.enssat.dayoff_manager.servlets;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Servlet affichant des stats sur les congés
 */
@WebServlet(
        name = "StatServlet",
        description = "StatServlet",
        urlPatterns = {"/stat-overview"}
)
public class StatServlet extends EnhancedHttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Dayoff> allDayoff = DaoProvider.getDayoffDao().getAll();
        List<DayoffType> dayoffTypes = DaoProvider.getDayoffTypeDao().getAll();
        int[] dataMonth = getDataPerMonth(allDayoff);

        req.setAttribute("componentNeeded", "stat-overview");
        req.setAttribute("dataMonth", dataMonth);
        req.setAttribute("dayoffs", allDayoff);
        req.setAttribute("dayoffTypes", dayoffTypes);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/template/index.jsp");
        dispatcher.forward(req, resp);
    }

    private int[] getDataPerMonth(List<Dayoff> allDayoff) {
        /*
        Retourne un tableau avec 12 index, un index représente un mois
        * */
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        int[] dataMonth = new int[12];
        // pas la peine d'init à 0 dans le cas ou AllDayoff == null car forcément 0 si non init pour le type int (oracle doc)

        if (allDayoff != null) {
            for (Dayoff dayoff : allDayoff) {
                calendarStart.setTime(dayoff.getDateStart());
                calendarEnd.setTime(dayoff.getDateEnd());

                // Calendar indexés avec 0 => janvier return 0
                if ((calendarStart.get(Calendar.MONTH) == calendarEnd.get(Calendar.MONTH)) && (calendarStart.get(Calendar.YEAR) == calendarEnd.get(Calendar.YEAR))) {
                    // si mois début et mois fin sont les mêmes => on ajoute la différence dans data
                    dataMonth[calendarStart.get(Calendar.MONTH)] += calendarEnd.get(Calendar.DAY_OF_MONTH) - calendarStart.get(Calendar.DAY_OF_MONTH);
                } else {
                    int nbMonth = (calendarEnd.get(Calendar.MONTH) - calendarStart.get(Calendar.MONTH));
                    if (nbMonth > 0) {
                        // jour début congé et jour fin congé sont dans la même année
                        while (nbMonth >= 0) {
                            dataMonth[calendarStart.get(Calendar.MONTH)] += calendarStart.getActualMaximum(Calendar.DAY_OF_MONTH) - calendarStart.get(Calendar.DAY_OF_MONTH);

                            calendarStart.set(Calendar.DAY_OF_MONTH, 0);
                            calendarStart.add(Calendar.MONTH, 1);
                            nbMonth--;
                        }
                    } else if (nbMonth < 0) {
                        // jour début congé et jour fin congé sont dans une année différente
                        nbMonth = Math.abs(nbMonth);
                        while (nbMonth >= 0) {
                            dataMonth[calendarStart.get(Calendar.MONTH)] += calendarStart.getActualMaximum(Calendar.DAY_OF_MONTH) - calendarStart.get(Calendar.DAY_OF_MONTH);

                            calendarStart.set(Calendar.DAY_OF_MONTH, 0);
                            if (calendarStart.get(Calendar.MONTH) == Calendar.DECEMBER) {
                                calendarStart.set(Calendar.MONTH, 0);
                            } else {
                                calendarStart.add(Calendar.MONTH, 1);
                            }
                            nbMonth--;
                        }

                    }

                }

            }
        }
        int sumDayoff = IntStream.of(dataMonth).sum();
        for (int i = 0; i < dataMonth.length; i++) {
            dataMonth[i] = (int) ((dataMonth[i] * 100.0) / (sumDayoff * 1.0));
        }
        return dataMonth;
    }

}
