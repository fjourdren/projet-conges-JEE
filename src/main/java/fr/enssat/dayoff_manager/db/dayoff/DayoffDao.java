package fr.enssat.dayoff_manager.db.dayoff;

import fr.enssat.dayoff_manager.db.GenericDao;

public interface DayoffDao extends GenericDao<Dayoff> {

    void validate(Dayoff dayoff, String comment);

    void refuse(Dayoff dayoff, String comment);
}
