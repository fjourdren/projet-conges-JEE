package fr.enssat.dayoff_manager.db.department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoMockImpl implements DepartmentDao {

    @Override
    public void save(Department entity) {
        //nothing
    }

    @Override
    public void delete(Department entity) {
        //nothing
    }

    @Override
    public Department findById(int id) {
        //TODO
        return null;
    }

    @Override
    public List<Department> getAll() {
        //TODO
        return new ArrayList<>();
    }
}
