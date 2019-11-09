package fr.enssat.dayoff_manager.db.dayoff_type;

import java.util.ArrayList;
import java.util.List;

public class DayoffTypeDaoMockImpl implements DayoffTypeDao {

    @Override
    public void save(DayoffType entity) {
        //nothing
    }

    @Override
    public void delete(DayoffType entity) {
        //nothing
    }

    @Override
    public DayoffType findById(int id) {
        //TODO
        return null;
    }

    @Override
    public List<DayoffType> getAll() {
        //TODO
        return new ArrayList<>();
    }
}
