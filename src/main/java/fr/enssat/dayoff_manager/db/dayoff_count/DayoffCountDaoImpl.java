package fr.enssat.dayoff_manager.db.dayoff_count;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.employee.Employee;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class DayoffCountDaoImpl extends GenericDaoImpl<DayoffCount> implements DayoffCountDao {

    public DayoffCountDaoImpl() {
        super(DayoffCount.class);
    }

    @Override
    public DayoffCount findByEmployeeAndDayoffType(Employee employee, DayoffType dayoffType) {
        String qlString = "SELECT x FROM DayoffCount x WHERE x.employee = :employee AND x.type = :type";
        TypedQuery<DayoffCount> query = em.createQuery(qlString, DayoffCount.class);
        query.setParameter("employee", employee);
        query.setParameter("type", dayoffType);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
