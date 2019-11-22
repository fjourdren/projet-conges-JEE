package fr.enssat.dayoff_manager;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffStatus;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@WebListener("DayoffAutoValidation")
public class DayoffAutoValidation extends TimerTask implements ServletContextListener {

    static {
        TimeUnit unit = TimeUnit.MILLISECONDS;
        TIMER_PERIOD = unit.convert(10, TimeUnit.SECONDS);
    }

    private static final long TIMER_PERIOD;
    private Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        timer = new Timer("DayoffAutoValidation");
        timer.scheduleAtFixedRate(this, TIMER_PERIOD, TIMER_PERIOD);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        timer.cancel();
    }

    @Override
    public void run() {
        System.out.println("DayoffAutoValidation RUN START");

        Calendar calendar = null;
        Date currentDate = new Date();
        for (Dayoff d : DaoProvider.getDayoffDao().getDayOffStatus(DayoffStatus.WAITING)) {
            calendar = Calendar.getInstance();
            calendar.setTime(d.getDateCreation());
            calendar.add(Calendar.DATE, 2);

            Date dateCreationPlusTwoDay = calendar.getTime();
            if (dateCreationPlusTwoDay.after(currentDate)) {
                DaoProvider.getDayoffDao().validate(d, "<validation automatique>");
            }
        }

        System.out.println("DayoffAutoValidation RUN END");
    }
}
