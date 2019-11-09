package fr.enssat.dayoff_manager.db.dayoff;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;

import java.util.Date;

public class DayoffDaoImpl extends GenericDaoImpl<Dayoff> implements DayoffDao {

    public DayoffDaoImpl() {
        super(Dayoff.class);
    }

    @Override
    public void validate(Dayoff dayoff, String comment) {
        if (dayoff == null) throw new IllegalArgumentException("dayoff must not be null");
        dayoff.setCommentRH(comment);
        dayoff.setStatus(DayoffStatus.ACCEPTED);
        dayoff.setDateValidation(new Date());
        save(dayoff);
    }

    @Override
    public void refuse(Dayoff dayoff, String comment) {
        if (dayoff == null) throw new IllegalArgumentException("dayoff must not be null");
        if (comment == null) throw new IllegalArgumentException("comment must not be null");

        dayoff.setCommentRH(comment);
        dayoff.setStatus(DayoffStatus.REFUSED);
        dayoff.setDateValidation(new Date());
        save(dayoff);
    }
}
