package fr.enssat.dayoff_manager.db.department;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentDaoMockImpl implements DepartmentDao {

    private List<Department> departmentList = new ArrayList<>();
    private int nextID = -1;

    public DepartmentDaoMockImpl() {
        Department imr2 = new Department("imr2");
        imr2.setId(++nextID);
        Department imr3 = new Department("imr3");
        imr3.setId(++nextID);

        departmentList.add(imr2);
        departmentList.add(imr3);
    }

    @Override
    public void save(Department entity) {
        entity.setId(++nextID);
        departmentList.add(entity);
    }

    @Override
    public void delete(Department entity) {
        departmentList.remove(entity);
    }

    @Override
    public Department findById(int id) {
        List<Department> res = departmentList.stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if (res.size() == 1) return res.get(0);
        return null;
    }

    @Override
    public List<Department> getAll() {
        return Collections.unmodifiableList(departmentList);
    }

    @Override
    public Department findByName(String name) {
        List<Department> res = departmentList.stream().filter(x -> x.getName() == name).collect(Collectors.toList());
        if (res.size() == 1) return res.get(0);
        return null;
    }
}
