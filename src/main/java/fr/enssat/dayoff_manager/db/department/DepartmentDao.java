package fr.enssat.dayoff_manager.db.department;

import fr.enssat.dayoff_manager.db.GenericDao;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.employee.Employee;

import java.util.List;

public interface DepartmentDao extends GenericDao<Department> {

    /**
     * Retourne tous les employés faisant partie du service
     *
     * @param department service
     * @return tous les employés faisant partie du service
     */
    List<Employee> getEmployees(Department department);

    /**
     * Retourne tous les congés du service spécifié en paramètre
     *
     * @param department service
     * @return tous les congés du service spécifié en paramètre
     */
    List<Dayoff> getDayOffs(Department department);

    /**
     * Recherche un département par nom
     *
     * @param name nom
     * @return déprtement si trouvé, sinon null
     */
    Department findByName(String name);

}
