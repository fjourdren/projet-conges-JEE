package fr.enssat.dayoff_manager.db.department;

import fr.enssat.dayoff_manager.db.GenericDao;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;

import java.util.List;

public interface DepartmentDao extends GenericDao<Department> {

    /**
     * Retourne tous les congés du service spécifié en paramètre
     *
     * @param department service
     * @return tous les congés du service spécifié en paramètre
     */
    List<Dayoff> getDayOffs(Department department);

}
