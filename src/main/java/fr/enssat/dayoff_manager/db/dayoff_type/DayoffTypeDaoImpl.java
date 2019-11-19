package fr.enssat.dayoff_manager.db.dayoff_type;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;

import javax.persistence.TypedQuery;
import java.util.List;

public class DayoffTypeDaoImpl extends GenericDaoImpl<DayoffType> implements DayoffTypeDao {

    public DayoffTypeDaoImpl() {
        super(DayoffType.class);
    }

    @Override
    public List<DayoffType> getAvailableDayoffTypes() {
        String qlString = "SELECT x FROM DayoffType x WHERE x.deleted = false";
        TypedQuery<DayoffType> query = em.createQuery(qlString, DayoffType.class);
        return query.getResultList();
    }
}
