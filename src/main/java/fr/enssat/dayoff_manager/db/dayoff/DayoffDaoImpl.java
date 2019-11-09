package fr.enssat.dayoff_manager.db.dayoff;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;

public class DayoffDaoImpl extends GenericDaoImpl<Dayoff> implements DayoffDao {

    public DayoffDaoImpl() {
        super(Dayoff.class);
    }
}
