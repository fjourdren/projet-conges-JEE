package fr.enssat.dayoff_manager.db.dayoff;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;
import fr.enssat.dayoff_manager.db.department.Department;
import fr.enssat.dayoff_manager.db.employee.Employee;

import java.util.Date;
import java.util.List;

public class DayoffDaoImpl extends GenericDaoImpl<Dayoff> implements DayoffDao {

    public DayoffDaoImpl() {
        super(Dayoff.class);
    }

    @Override
    public void validate(Dayoff dayoff, String comment) {
        if (dayoff == null) throw new IllegalArgumentException("dayoff must not be null");
        dayoff.setCommentRH(comment);
        dayoff.setStatus(DayoffStatus.ACCEPTED);
        dayoff.setDateValidation(new Date());
        save(dayoff);
    }

    @Override
    public void refuse(Dayoff dayoff, String comment) {
        if (dayoff == null) throw new IllegalArgumentException("dayoff must not be null");
        if (comment == null) throw new IllegalArgumentException("comment must not be null");

        dayoff.setCommentRH(comment);
        dayoff.setStatus(DayoffStatus.REFUSED);
        dayoff.setDateValidation(new Date());
        save(dayoff);
    }

    /**
     * Sauvegarde l'entité en base
     */
    @Override
    public void save(Dayoff dayoff) {
        save(dayoff);
    }

    /**
     * Supprime l'entité en base
     */
    @Override
    public void delete(Dayoff dayoff) {
        delete(dayoff);
    }

    /**
     * Retourne l'entité ayant l'ID spécifié en paramètre
     *
     * @param id ID
     * @return entité ayant l'ID spécifié en paramètre
     */
    @Override
    public Dayoff findById(int id) {
        return em.find(entityClass, id);
    }

    /**
     * Retourne tous les objets de l'entité stockés en base
     *
     * @return tous les objets de l'entité stockés en base
     */

    public List<Dayoff> getAllDayoff() {
        String qlString = String.format("SELECT x FROM %s x", entityClass.getSimpleName());
        return em.createQuery(qlString, entityClass).getResultList();
    }

    public List<Dayoff> getEmployeeDayoff(Employee employee){
        String qlString = String.format("SELECT dayoff.x FROM dayoff, employee WHERE employee.id = dayoff.employeeId AND employee.id = %s;", employee.getId());
        return em.createQuery(qlString, entityClass).getResultList();
    }

    public List<Dayoff> getDepartmentDayoff(Department department){
        String qlString = String.format("SELECT dayoff.x FROM dayoff, employee, department WHERE employee.id = dayoff.employeeId AND employee.id_department = department.id AND department.id = %s;", department.getId());
        return em.createQuery(qlString, entityClass).getResultList();
    }
}
