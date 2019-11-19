package fr.enssat.dayoff_manager.db.dayoff_count;

import fr.enssat.dayoff_manager.db.GenericDaoMockImpl;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.employee.Employee;

public class DayoffCountDaoMockImpl extends GenericDaoMockImpl<DayoffCount> implements DayoffCountDao {

    @Override
    public void newEntityConstraintsCheck(DayoffCount entity) {
        for (DayoffCount count : getAll()) {
            if (count.getType().equals(entity.getType()) && count.getEmployee().equals(entity.getEmployee())) {
                throw new RuntimeException("item already exists");
            }
        }
    }

    @Override
    public DayoffCount findByEmployeeAndDayoffType(Employee employee, DayoffType dayoffType) {
        for (DayoffCount count : getAll()) {
            if (count.getEmployee().equals(employee) && count.getType().equals(dayoffType)) {
                return count;
            }
        }
        return null;
    }
}
