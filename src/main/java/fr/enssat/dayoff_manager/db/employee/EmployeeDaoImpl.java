package fr.enssat.dayoff_manager.db.employee;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;

import java.util.List;

public class EmployeeDaoImpl extends GenericDaoImpl<Employee> implements EmployeeDao {

    public EmployeeDaoImpl() {
        super(Employee.class);
    }

    @Override
    public Employee login(String username, String password) {
        //TODO
        return null;
    }

    @Override
    public List<Dayoff> getDayOffs(Employee employee) {
        //TODO
        return null;
    }

    @Override
    public Float nbDaysUsable(Employee employee, DayoffType type) {
        //TODO
        return null;
    }
}
