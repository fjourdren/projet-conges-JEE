package fr.enssat.dayoff_manager.db.dayoff_count;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;

public class DayoffCountDaoImpl extends GenericDaoImpl<DayoffCount> implements DayoffCountDao {

    public DayoffCountDaoImpl() {
        super(DayoffCount.class);
    }
}
