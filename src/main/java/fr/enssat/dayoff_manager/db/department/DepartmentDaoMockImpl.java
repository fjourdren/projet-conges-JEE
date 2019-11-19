package fr.enssat.dayoff_manager.db.department;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.GenericDaoMockImpl;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoMockImpl extends GenericDaoMockImpl<Department> implements DepartmentDao {

    @Override
    public void save(Department entity) {
        for (Department department : getAll()) {
            if (department.getName().equals(entity.getName())) {
                throw new RuntimeException("A department with name" + entity.getName() + " already exists");
            }
        }

        super.save(entity);
    }

    @Override
    public List<Dayoff> getDayOffs(Department department) {
        List<Dayoff> res = new ArrayList<>();
        for (Dayoff dayoff : DaoProvider.getDayoffDao().getAll()) {
            if (dayoff.getEmployee().getDepartment().equals(department)) {
                res.add(dayoff);
            }
        }
        return res;
    }
}
