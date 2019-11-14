package fr.enssat.dayoff_manager.db.employee;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDao;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Date;

public class EmployeeDaoImpl extends GenericDaoImpl<Employee> implements EmployeeDao {

    private static DayoffDaoImpl dayoffDao = new DayoffDaoImpl();

    public EmployeeDaoImpl() {
        super(Employee.class);
    }

    /**
     * Sauvegarde l'entité en base
     *
     * @param entity entité
     */
    @Override
    public void save(Employee entity) {
        em.persist(entity);
    }

    /**
     * Supprime l'entité en base
     *
     * @param entity entité
     */
    @Override
    public void delete(Employee entity) {
        em.remove(entity);
    }

    /**
     * Retourne l'entité ayant l'ID spécifié en paramètre
     *
     * @param id ID
     * @return entité ayant l'ID spécifié en paramètre
     */
    @Override
    public Employee findById(int id) {
        return em.find(entityClass, id);
    }

    /**
     * Retourne tous les objets de l'entité stockés en base
     *
     * @return tous les objets de l'entité stockés en base
     */

    public List<Employee> getAll() {
        String qlString = String.format("SELECT x FROM %s x", entityClass.getSimpleName());
        return em.createQuery(qlString, entityClass).getResultList();
    }


    public boolean login(String email, String password) {
        String qlString = String.format("SELECT x FROM %s x", entityClass.getSimpleName());
        if(em.createQuery(qlString, entityClass).getResultList().isEmpty()){
            return false;
        }
        return true;
    }


    public void createLeave(DayoffType typeDayoff, int nbDays, java.util.Date dateStart, java.util.Date dateEnd){
        Dayoff send = new Dayoff();
        send.setDateCreation(new java.util.Date());
        send.setDateStart(dateStart);
        send.setDateEnd(dateEnd);
        send.setNbDays(nbDays);
        send.setType(typeDayoff);
        dayoffDao.save(send);
    }


    public List<Employee> getDayoff(){
        String qlString = String.format("SELECT dayoff.x FROM dayoff, employee WHERE employee.id = dayoff.employeeId;");
        return em.createQuery(qlString, entityClass).getResultList();
    }


    public int nbDaysUsable(DayoffType type){
        String qlString = String.format("SELECT defaultNbDays FROM dayofftype WHERE name = %s;", type.getName());
        return em.createQuery(qlString, entityClass).getFirstResult();
    }


    public void modifyLeaveDemand(Dayoff dayoff, Dayoff newDayoff){
        dayoffDao.delete(dayoff);
        dayoffDao.save(newDayoff);
    }


    public void deleteLeaveDemand(Dayoff dayoff){
        dayoffDao.delete(dayoff);
    }
}
