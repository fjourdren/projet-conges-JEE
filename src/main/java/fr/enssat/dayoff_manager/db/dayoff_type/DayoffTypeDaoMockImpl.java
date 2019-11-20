package fr.enssat.dayoff_manager.db.dayoff_type;

import fr.enssat.dayoff_manager.db.GenericDaoMockImpl;

import java.util.ArrayList;
import java.util.List;

public class DayoffTypeDaoMockImpl extends GenericDaoMockImpl<DayoffType> implements DayoffTypeDao {

    @Override
    public void newEntityConstraintsCheck(DayoffType entity) {
        for (DayoffType type : getAll()) {
            if (type.getName().equals(entity.getName())) {
                throw new RuntimeException("A dayoff with name " + entity.getName() + " already exists");
            }
        }
    }

    @Override
    public List<DayoffType> getAvailableDayoffTypes() {
        List<DayoffType> res = new ArrayList<>();
        for (DayoffType type : getAll()) {
            if (!type.isDeleted()) {
                res.add(type);
            }
        }

        return res;
    }
}
