package fr.enssat.dayoff_manager.db;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

/**
 * DAO de base pour une entité
 *
 * @param <E> type de l'entité
 */
public abstract class GenericDaoImpl<E extends GenericEntity> implements GenericDao<E> {

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
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    /**
     * Supprime l'entité en base
     *
     * @param entity entité
     */
    @Override
    public void delete(E entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    /**
     * Retourne l'entité ayant l'ID spécifié en paramètre
     *
     * @param id ID
     * @return entité ayant l'ID spécifié en paramètre
     */
    @Override
    public E findById(Long id) {
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
}
