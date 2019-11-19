package fr.enssat.dayoff_manager.db.dayoff_type;

import fr.enssat.dayoff_manager.db.GenericDaoMockImpl;

public class DayoffTypeDaoMockImpl extends GenericDaoMockImpl<DayoffType> implements DayoffTypeDao {

    @Override
    public void save(DayoffType entity) {
        for (DayoffType type : getAll()) {
            if (type.getName().equals(entity.getName())) {
                throw new RuntimeException("A dayoff with name " + entity.getName() + " already exists");
            }
        }
        super.save(entity);
    }
}
