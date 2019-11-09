package fr.enssat.dayoff_manager.db;

import fr.enssat.dayoff_manager.db.dayoff.DayoffDao;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCountDao;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCountDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffTypeDao;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffTypeDaoImpl;
import fr.enssat.dayoff_manager.db.department.DepartmentDao;
import fr.enssat.dayoff_manager.db.department.DepartmentDaoImpl;
import fr.enssat.dayoff_manager.db.employee.EmployeeDao;
import fr.enssat.dayoff_manager.db.employee.EmployeeDaoImpl;

public class DaoProvider {

    static {
        DAYOFF_DAO = new DayoffDaoImpl();
        DAYOFF_COUNT_DAO = new DayoffCountDaoImpl();
        DAYOFF_TYPE_DAO = new DayoffTypeDaoImpl();
        DEPARTMENT_DAO = new DepartmentDaoImpl();
        EMPLOYEE_DAO = new EmployeeDaoImpl();
    }

    private static final DayoffDao DAYOFF_DAO;
    private static final DayoffCountDao DAYOFF_COUNT_DAO;
    private static final DayoffTypeDao DAYOFF_TYPE_DAO;
    private static final DepartmentDao DEPARTMENT_DAO;
    private static final EmployeeDao EMPLOYEE_DAO;

    public static DayoffDao getDayoffDao() {
        return DAYOFF_DAO;
    }

    public static DayoffCountDao getDayoffCountDao() {
        return DAYOFF_COUNT_DAO;
    }

    public static DayoffTypeDao getDayoffTypeDao() {
        return DAYOFF_TYPE_DAO;
    }

    public static DepartmentDao getDepartmentDao() {
        return DEPARTMENT_DAO;
    }

    public static EmployeeDao getEmployeeDao() {
        return EMPLOYEE_DAO;
    }
}
