package fr.enssat.dayoff_manager.db.employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoMockImpl implements EmployeeDao {

    @Override
    public void save(Employee entity) {
        //nothing
    }

    @Override
    public void delete(Employee entity) {
        //nothing
    }

    @Override
    public Employee findById(int id) {
        //TODO
        return null;
    }

    @Override
    public List<Employee> getAll() {
        //TODO
        return new ArrayList<>();
    }
}
