package fr.enssat.dayoff_manager.db.dayoff;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.GenericDaoMockImpl;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DayoffDaoMockImpl extends GenericDaoMockImpl<Dayoff> implements DayoffDao {

    @Override
    public void newEntityConstraintsCheck(Dayoff entity) {
        //TODO contrôler validité dates...
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

    @Override
    public List<Dayoff> getDayOffStatus(DayoffStatus status) {
        List<Dayoff> dayoffs = new ArrayList<>();

        for (Dayoff dayoff : DaoProvider.getDayoffDao().getAll()) {
            if (dayoff.getStatus() == status) {
                dayoffs.add(dayoff);
            }
        }
        return dayoffs;
    }

    @Override
    public void save(Dayoff entity) {
        if(entity.getDateCreation().after(entity.getDateStart()) ||
                entity.getDateCreation().after(entity.getDateEnd()) ||
                entity.getDateStart().after(entity.getDateEnd()) ||
                entity.getDateCreation().after(new Date(entity.getDateStart().getTime() + (1000 * 60 * 60 * 24 * 2)))
        )
            return;


        super.save(entity);
    }
}
