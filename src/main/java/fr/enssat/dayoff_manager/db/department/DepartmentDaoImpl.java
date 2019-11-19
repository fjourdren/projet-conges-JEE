package fr.enssat.dayoff_manager.db.department;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class DepartmentDaoImpl extends GenericDaoImpl<Department> implements DepartmentDao {

    public DepartmentDaoImpl() {
        super(Department.class);
    }

    @Override
    public List<Dayoff> getDayOffs(Department department) {
        String qlString = "SELECT x FROM Dayoff x WHERE x.employee.id_department = :dep_id";
        TypedQuery<Dayoff> query = em.createQuery(qlString, Dayoff.class);
        query.setParameter("dep_id", department.getId());
        return query.getResultList();
    }

    @Override
    public Department findByName(String name) {
        String qlString = "SELECT x FROM Department x WHERE x.name = :name";
        TypedQuery<Department> query = em.createQuery(qlString, Department.class);
        query.setParameter("name", name);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
