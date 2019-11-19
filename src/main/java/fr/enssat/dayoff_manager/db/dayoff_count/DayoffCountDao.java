package fr.enssat.dayoff_manager.db.dayoff_count;

import fr.enssat.dayoff_manager.db.GenericDao;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.employee.Employee;

public interface DayoffCountDao extends GenericDao<DayoffCount> {

    /**
     * Recherche l'entré avec l'employé et le type de congés spécifiés en paramètre
     *
     * @param employee   employé
     * @param dayoffType type de congés
     * @return entré avec l'employé et le type de congés spécifiés en paramètre, null si non existante
     */
    DayoffCount findByEmployeeAndDayoffType(Employee employee, DayoffType dayoffType);
}
