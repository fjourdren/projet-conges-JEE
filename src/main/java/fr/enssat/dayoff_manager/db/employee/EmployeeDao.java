package fr.enssat.dayoff_manager.db.employee;

import fr.enssat.dayoff_manager.db.GenericDao;

import java.util.List;

public interface EmployeeDao extends GenericDao<Employee> {
    @Override
    void save(Employee entity);

    @Override
    void delete(Employee entity);

    @Override
    Employee findById(int id);

    @Override
    List<Employee> getAll();


}
