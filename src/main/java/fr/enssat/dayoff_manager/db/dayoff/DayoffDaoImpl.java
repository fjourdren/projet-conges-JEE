package fr.enssat.dayoff_manager.db.dayoff;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;

import java.util.Objects;

public class DayoffDaoImpl extends GenericDaoImpl<Dayoff> implements DayoffDao {

    public DayoffDaoImpl() {
        super(Dayoff.class);
    }

    @Override
    public void validate(Dayoff dayoff, String rhComment) {
        Objects.requireNonNull(dayoff);
        dayoff.setDateValidation(new java.util.Date());
        dayoff.setCommentRH(rhComment);
        dayoff.setStatus(DayoffStatus.ACCEPTED);
        save(dayoff);
    }

    @Override
    public void refuse(Dayoff dayoff, String rhComment) {
        Objects.requireNonNull(dayoff);
        Objects.requireNonNull(rhComment);
        dayoff.setDateValidation(new java.util.Date());
        dayoff.setCommentRH(rhComment);
        dayoff.setStatus(DayoffStatus.REFUSED);
        save(dayoff);
    }
}
