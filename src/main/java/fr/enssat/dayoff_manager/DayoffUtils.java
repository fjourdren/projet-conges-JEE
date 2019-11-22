package fr.enssat.dayoff_manager;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DayoffUtils {

    /**
     * Vérifie si la nouvelle demande de congés peut être ajouté ou non
     *
     * @param dayoff nouvelle demande de congés
     * @return true/false
     */
    public static boolean canAddNewDayoff(Dayoff dayoff) {
        if (dayoff.getDateCreation().after(dayoff.getDateStart()) ||
                dayoff.getDateCreation().after(dayoff.getDateEnd()) ||
                dayoff.getDateStart().after(dayoff.getDateEnd()) ||
                dayoff.getDateCreation().after(new Date(dayoff.getDateStart().getTime() + (1000 * 60 * 60 * 24 * 2)))
        ) {
            return false;
        }

        List<Dayoff> dayoffs = DaoProvider.getEmployeeDao().getDayOffs(dayoff.getEmployee());
        for (Dayoff conge : dayoffs) {
            if (dayoff.getDateStart().after(conge.getDateStart()) && dayoff.getDateStart().before(conge.getDateEnd()))
                return false;
            if (dayoff.getDateEnd().after(conge.getDateStart()) && dayoff.getDateEnd().before(conge.getDateEnd()))
                return false;
        }
        return true;
    }

    /**
     * Calcule la durée en jours de la demande de congés passé en paramètre
     *
     * @param dayoff demande de congés
     * @return durée en jours
     */
    public static float calcNbDays(Dayoff dayoff) {
        float nbrDay = getWorkingDaysBetweenTwoDates(dayoff.getDateStart(), dayoff.getDateEnd());
        Calendar startDayCalendar = Calendar.getInstance(), endDayCalendar = Calendar.getInstance();
        startDayCalendar.setTime(dayoff.getDateStart());
        endDayCalendar.setTime(dayoff.getDateEnd());

        if (startDayCalendar.get(Calendar.HOUR_OF_DAY) == 12 && endDayCalendar.get(Calendar.HOUR_OF_DAY) == 0)
            return nbrDay - 0.5f;
        if (startDayCalendar.get(Calendar.HOUR_OF_DAY) == 0 && endDayCalendar.get(Calendar.HOUR_OF_DAY) == 12)
            return nbrDay - 0.5f;
        return nbrDay;
    }

    /**
     * Calcule le nombre de jours ouvrés entre deux dates
     *
     * @param startDate date de début
     * @param endDate   date de fin
     * @return nombre de jours ouvrés entre deux dates
     */
    private static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int workDays = 0;

        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }

        do {
            //excluding start date
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++workDays;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

        return workDays;
    }
}
