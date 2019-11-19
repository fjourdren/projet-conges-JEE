package fr.enssat.dayoff_manager.db.employee;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.GenericDaoMockImpl;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCount;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoMockImpl extends GenericDaoMockImpl<Employee> implements EmployeeDao {

    @Override
    public void newEntityConstraintsCheck(Employee entity) {
        for (Employee employee : getAll()) {
            if (employee.getEmail().equals(entity.getEmail())) {
                throw new RuntimeException("A employee with mail" + entity.getEmail() + " already exists");
            }
        }
    }

    @Override
    public Employee login(String email, String sha256Password) {
        for (Employee employee : getAll()) {
            if (employee.getEmail().equals(email) && employee.getSha256Password().equals(sha256Password)) {
                return employee;
            }
        }
        return null;

    }

    @Override
    public List<Dayoff> getDayOffs(Employee employee) {
        List<Dayoff> dayoffs = new ArrayList<>();

        for (Dayoff dayoff : DaoProvider.getDayoffDao().getAll()) {
            if (dayoff.getEmployee().getId() == employee.getId()) {
                dayoffs.add(dayoff);
            }
        }

        return dayoffs;
    }

    @Override
    public Float nbDaysUsable(Employee employee, DayoffType type) {
        for (DayoffCount dayoffCount : DaoProvider.getDayoffCountDao().getAll()) {
            if (dayoffCount.getType().getId() == type.getId()
                    && dayoffCount.getEmployee().getId() == employee.getId()) {
                return dayoffCount.getNbDays();
            }
        }
        return type.getDefaultNbDays();
    }
}
