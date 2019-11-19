package fr.enssat.dayoff_manager.db;

import fr.enssat.dayoff_manager.Utils;
import fr.enssat.dayoff_manager.db.dayoff.DayoffDao;
import fr.enssat.dayoff_manager.db.dayoff.*;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCountDao;
import fr.enssat.dayoff_manager.db.dayoff_count.*;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffTypeDao;
import fr.enssat.dayoff_manager.db.dayoff_type.*;
import fr.enssat.dayoff_manager.db.department.DepartmentDao;
import fr.enssat.dayoff_manager.db.department.*;
import fr.enssat.dayoff_manager.db.employee.EmployeeDao;
import fr.enssat.dayoff_manager.db.employee.*;

public class DaoProvider {

    static {
        DAYOFF_DAO = new DayoffDaoMockImpl();
        DAYOFF_COUNT_DAO = new DayoffCountDaoMockImpl();
        DAYOFF_TYPE_DAO = new DayoffTypeDaoMockImpl();
        DEPARTMENT_DAO = new DepartmentDaoMockImpl();
        EMPLOYEE_DAO = new EmployeeDaoMockImpl();

        Utils.fillDBWithSomeData();
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
