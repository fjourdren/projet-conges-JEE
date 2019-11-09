package fr.enssat.dayoff_manager.db.dayoff;

/**
 * Statut d'une demande de congé
 */
public enum DayoffStatus {

    /**
     * Demande en attente de validation par le service RH
     */
    WAITING,

    /**
     * Demande acceptée par le service RH
     */
    ACCEPTED,

    /**
     * Demande refusée par le service RH
     */
    REFUSED
}
