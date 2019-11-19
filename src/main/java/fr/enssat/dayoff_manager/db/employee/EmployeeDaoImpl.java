package fr.enssat.dayoff_manager.db.employee;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCount;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeDaoImpl extends GenericDaoImpl<Employee> implements EmployeeDao {

    public EmployeeDaoImpl() {
        super(Employee.class);
    }

    @Override
    public Employee login(String email, String sha256Password) {
        String qlString = "SELECT x FROM Employee x WHERE x.email = :email AND x.sha256Password = :sha256Password";
        TypedQuery<Employee> query = em.createQuery(qlString, Employee.class);
        query.setParameter("email", email);
        query.setParameter("sha256Password", sha256Password);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Dayoff> getDayOffs(Employee employee) {
        String qlString = "SELECT x FROM Dayoff x WHERE x.id_employee = :employee_id";
        TypedQuery<Dayoff> query = em.createQuery(qlString, Dayoff.class);
        query.setParameter("employee_id", employee.getId());
        return query.getResultList();
    }

    @Override
    public Float nbDaysUsable(Employee employee, DayoffType type) {
        String qlString = "SELECT x FROM DayoffCount x WHERE x.id_employee = :employee_id AND x.id_type = :type_id";
        TypedQuery<DayoffCount> query = em.createQuery(qlString, DayoffCount.class);
        query.setParameter("employee_id", employee.getId());
        query.setParameter("type_id", type.getId());

        try {
            return query.getSingleResult().getNbDays();
        } catch (NoResultException e) {
            return type.getDefaultNbDays();
        }
    }
}
