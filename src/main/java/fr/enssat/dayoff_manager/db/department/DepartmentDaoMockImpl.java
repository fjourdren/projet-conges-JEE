package fr.enssat.dayoff_manager.db.department;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.GenericDaoMockImpl;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.employee.Employee;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoMockImpl extends GenericDaoMockImpl<Department> implements DepartmentDao {

    @Override
    public void newEntityConstraintsCheck(Department entity) {
        for (Department department : getAll()) {
            if (department.getName().equals(entity.getName())) {
                throw new RuntimeException("A department with name" + entity.getName() + " already exists");
            }
        }
    }

    @Override
    public List<Employee> getEmployees(Department department) {
        List<Employee> res = new ArrayList<>();
        for (Employee employee : DaoProvider.getEmployeeDao().getAll()) {
            if (employee.getDepartment().getId() == department.getId()) {
                res.add(employee);
            }
        }
        return res;

    }

    @Override
    public List<Dayoff> getDayOffs(Department department) {
        List<Dayoff> res = new ArrayList<>();
        for (Dayoff dayoff : DaoProvider.getDayoffDao().getAll()) {
            if (dayoff.getEmployee().getDepartment().getId() == department.getId()) {
                res.add(dayoff);
            }
        }
        return res;
    }

    @Override
    public Department findByName(String name) {
        for (Department department : getAll()) {
            if (department.getName().equals(name)) {
                return department;
            }
        }
        return null;
    }
}
