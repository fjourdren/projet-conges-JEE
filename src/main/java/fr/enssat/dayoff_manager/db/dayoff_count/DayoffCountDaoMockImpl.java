package fr.enssat.dayoff_manager.db.dayoff_count;

import fr.enssat.dayoff_manager.db.GenericDaoMockImpl;

public class DayoffCountDaoMockImpl extends GenericDaoMockImpl<DayoffCount> implements DayoffCountDao {

    @Override
    public void save(DayoffCount entity) {
        for (DayoffCount count : getAll()) {
            if (count.getType().equals(entity.getType()) && count.getEmployee().equals(entity.getEmployee())) {
                throw new RuntimeException("item already exists");
            }
        }

        super.save(entity);
    }
}
