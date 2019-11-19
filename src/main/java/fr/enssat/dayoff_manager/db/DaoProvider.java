package fr.enssat.dayoff_manager.db;

import fr.enssat.dayoff_manager.Utils;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDao;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDaoMockImpl;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCountDao;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCountDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCountDaoMockImpl;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffTypeDao;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffTypeDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffTypeDaoMockImpl;
import fr.enssat.dayoff_manager.db.department.DepartmentDao;
import fr.enssat.dayoff_manager.db.department.DepartmentDaoImpl;
import fr.enssat.dayoff_manager.db.department.DepartmentDaoMockImpl;
import fr.enssat.dayoff_manager.db.employee.EmployeeDao;
import fr.enssat.dayoff_manager.db.employee.EmployeeDaoImpl;
import fr.enssat.dayoff_manager.db.employee.EmployeeDaoMockImpl;

public class DaoProvider {

    private static final boolean MOCK_MODE = true;
    private static final DayoffDao DAYOFF_DAO;
    private static final DayoffCountDao DAYOFF_COUNT_DAO;
    private static final DayoffTypeDao DAYOFF_TYPE_DAO;
    private static final DepartmentDao DEPARTMENT_DAO;
    private static final EmployeeDao EMPLOYEE_DAO;

    static {
        if (MOCK_MODE) {
            DAYOFF_DAO = new DayoffDaoMockImpl();
            DAYOFF_COUNT_DAO = new DayoffCountDaoMockImpl();
            DAYOFF_TYPE_DAO = new DayoffTypeDaoMockImpl();
            DEPARTMENT_DAO = new DepartmentDaoMockImpl();
            EMPLOYEE_DAO = new EmployeeDaoMockImpl();
        } else {
            DAYOFF_DAO = new DayoffDaoImpl();
            DAYOFF_COUNT_DAO = new DayoffCountDaoImpl();
            DAYOFF_TYPE_DAO = new DayoffTypeDaoImpl();
            DEPARTMENT_DAO = new DepartmentDaoImpl();
            EMPLOYEE_DAO = new EmployeeDaoImpl();
        }
    }

    private static boolean lazyInitDone = false;

    private static void lazyInitIfNecessary() {
        if (!lazyInitDone) {
            lazyInitDone = true;
            if (EMPLOYEE_DAO.getAll().size() == 0) {
                System.err.println("Database seems empty... Let me fill it with some data !");
                Utils.fillDBWithSomeData();
            }
        }
    }

    public static DayoffDao getDayoffDao() {
        lazyInitIfNecessary();
        return DAYOFF_DAO;
    }

    public static DayoffCountDao getDayoffCountDao() {
        lazyInitIfNecessary();
        return DAYOFF_COUNT_DAO;
    }

    public static DayoffTypeDao getDayoffTypeDao() {
        lazyInitIfNecessary();
        return DAYOFF_TYPE_DAO;
    }

    public static DepartmentDao getDepartmentDao() {
        lazyInitIfNecessary();
        return DEPARTMENT_DAO;
    }

    public static EmployeeDao getEmployeeDao() {
        lazyInitIfNecessary();
        return EMPLOYEE_DAO;
    }
}
