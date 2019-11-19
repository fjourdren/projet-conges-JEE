package fr.enssat.dayoff_manager.db.dayoff;

import fr.enssat.dayoff_manager.db.GenericDaoMockImpl;

import java.util.Objects;

public class DayoffDaoMockImpl extends GenericDaoMockImpl<Dayoff> implements DayoffDao {

    @Override
    public void save(Dayoff entity) {
        //TODO contrôler validité dates...
        super.save(entity);
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
