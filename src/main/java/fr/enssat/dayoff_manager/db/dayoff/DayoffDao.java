package fr.enssat.dayoff_manager.db.dayoff;

import fr.enssat.dayoff_manager.db.GenericDao;

public interface DayoffDao extends GenericDao<Dayoff> {

    /**
     * Valide une demande de congés
     *
     * @param dayoff    demande de congés
     * @param rhComment commentaire RH (facultatif)
     */
    void validate(Dayoff dayoff, String rhComment);

    /**
     * Refuse une demande de congés
     *
     * @param dayoff    demande de congés
     * @param rhComment commentaire RH (obligatoire)
     */
    void refuse(Dayoff dayoff, String rhComment);

}
