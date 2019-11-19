package fr.enssat.dayoff_manager.db.dayoff_type;

import fr.enssat.dayoff_manager.db.GenericDao;

import java.util.List;

public interface DayoffTypeDao extends GenericDao<DayoffType> {

    /**
     * Retourne tous les types sauf ceux marqués comme supprimé
     *
     * @return tous les types sauf ceux marqués comme supprimé
     */
    List<DayoffType> getAvailableDayoffTypes();
}
