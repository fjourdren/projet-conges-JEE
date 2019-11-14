package fr.enssat.dayoff_manager.db;

import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDao;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDaoMockImpl;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Date;

/**
 * DAO de base pour une entité
 *
 * @param <E> type de l'entité
 */
public class GenericDaoImpl<E> implements GenericDao<E> {

    /**
     * Classe de l'entité
     */
    protected Class<E> entityClass;

    /**
     * EntityManager
     */
    protected static final EntityManager em;

    static {
        // le nom doit être synchronisé avec celui dans persistence.xml
        em = Persistence.createEntityManagerFactory("dayoff_manager").createEntityManager();
    }

    public GenericDaoImpl(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Sauvegarde l'entité en base
     *
     * @param entity entité
     */
    @Override
    public void save(E entity) {
        em.persist(entity);
    }

    /**
     * Supprime l'entité en base
     *
     * @param entity entité
     */
    @Override
    public void delete(E entity) {
        em.remove(entity);
    }

    /**
     * Retourne l'entité ayant l'ID spécifié en paramètre
     *
     * @param id ID
     * @return entité ayant l'ID spécifié en paramètre
     */
    @Override
    public E findById(int id) {
        return em.find(entityClass, id);
    }

    /**
     * Retourne tous les objets de l'entité stockés en base
     *
     * @return tous les objets de l'entité stockés en base
     */
    @Override
    public List<E> getAll() {
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
        Dayoff send = new Dayoff((dateEnd, dateStart, nbDays));
        DayoffDaoImpl.save(send);
    }

    public List<E> getDayoff(){
        String qlString = String.format("SELECT dayoff.x FROM dayoff, employee WHERE employee.id = dayoff.employeeId;");
        return em.createQuery(qlString, entityClass).getResultList();
    }

    public int nbDaysUsable(DayoffType type){
        String qlString = String.format("SELECT defaultNbDays FROM dayofftype WHERE name = %s;", type.getName());
        return em.createQuery(qlString, entityClass).getFirstResult();
    }

    public void modifyLeaveDemand(Dayoff dayoff, Dayoff newDayoff){
        DayoffDaoImpl.delete(dayoff);
        DayoffDao.save(newDayoff);
    }

    public void deleteLeaveDemand(Dayoff dayoff){
        DayoffDaoImpl.delete(dayoff);
    }
}
