package fr.enssat.dayoff_manager.db.dayoff_count;

import java.util.ArrayList;
import java.util.List;

public class DayoffCountDaoMockImpl implements DayoffCountDao {

    @Override
    public void save(DayoffCount entity) {
        //nothing
    }

    @Override
    public void delete(DayoffCount entity) {
        //nothing
    }

    @Override
    public DayoffCount findById(int id) {
        //TODO
        return null;
    }

    @Override
    public List<DayoffCount> getAll() {
        //TODO
        return new ArrayList<>();
    }
}
