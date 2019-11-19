package fr.enssat.dayoff_manager.db;

import java.util.List;

/**
 * Interface décrivant un DAO de base pour une entité
 *
 * @param <E> type de l'entité
 */
public interface GenericDao<E extends GenericEntity> {

    /**
     * Sauvegarde l'entité en base
     *
     * @param entity entité
     */
    void save(E entity);

    /**
     * Supprime l'entité en base
     *
     * @param entity entité
     */
    void delete(E entity);

    /**
     * Retourne l'entité ayant l'ID spécifié en paramètre
     *
     * @param id ID
     * @return entité ayant l'ID spécifié en paramètre
     */
    E findById(Long id);

    /**
     * Retourne tous les objets de l'entité stockés en base
     *
     * @return tous les objets de l'entité stockés en base
     */
    List<E> getAll();
}
