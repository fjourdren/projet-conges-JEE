package fr.enssat.dayoff_manager.db.department;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;

public class DepartmentDaoImpl extends GenericDaoImpl<Department> implements DepartmentDao {

    public DepartmentDaoImpl() {
        super(Department.class);
    }
}
