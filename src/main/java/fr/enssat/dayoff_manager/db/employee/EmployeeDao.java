package fr.enssat.dayoff_manager.db.employee;

import fr.enssat.dayoff_manager.db.GenericDao;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;

import java.util.List;

public interface EmployeeDao extends GenericDao<Employee> {

    /**
     * Recherche dans la base un utilisateur ayant le login et le mot de passe spécifié en paramètre
     *
     * @param username login
     * @param password mot de passe
     * @return utilisateur authentifié ou null si username ou password faux
     */
    Employee login(String username, String password);

    /**
     * Retourne tous les congés de l'employée spécifié en paramètre
     *
     * @param employee employée
     * @return tous les congés de l'employée spécifié en paramètre
     */
    List<Dayoff> getDayOffs(Employee employee);

    /**
     * Retourne le nombre de jours de congés disponibles pour le type de congés passé en paramètre
     *
     * @param employee employée
     * @param type type
     * @return nombre de jours de congés disponibles pour le type de congés passé en paramètre, null si aucune limite
     */
    Float nbDaysUsable(Employee employee, DayoffType type);
}
