package fr.enssat.dayoff_manager.db.employee;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;

public class EmployeeDaoImpl extends GenericDaoImpl<Employee> implements EmployeeDao {

    public EmployeeDaoImpl() {
        super(Employee.class);
    }
}
