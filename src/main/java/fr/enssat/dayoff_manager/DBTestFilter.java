package fr.enssat.dayoff_manager;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffStatus;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCount;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.department.Department;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@Deprecated
public class DBTestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[DBTestFilter] init DB...");
        runTests();
        System.out.println("[DBTestFilter] OK!!!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private void runTests() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("dayoff_manager");
        EntityManager manager = factory.createEntityManager();

        Department department = new Department("LOLILOL");
        Employee employee = new Employee("first", "end", "pass", "adr", "pos", "email@", EmployeeType.BOSS, department);
        DayoffType type = new DayoffType("type", 5f,false);
        Dayoff dayoff = new Dayoff(new Date(), new Date(), new Date(), new Date(), 5, DayoffStatus.WAITING, "comment", "aie", type, employee);
        DayoffCount dayoffCount = new DayoffCount(0f, type, employee);

        manager.getTransaction().begin();
        manager.persist(department);
        manager.persist(employee);
        manager.persist(type);
        manager.persist(dayoff);
        manager.persist(dayoffCount);
        manager.getTransaction().commit();

      /*  System.out.println(Arrays.toString(new GenericDaoImpl<>(Department.class).getAll().toArray()));
        System.out.println("---");
        System.out.println(Arrays.toString(new GenericDaoImpl<>(Employee.class).getAll().toArray()));
        System.out.println("---");
        System.out.println(Arrays.toString(new GenericDaoImpl<>(DayoffType.class).getAll().toArray()));
        System.out.println("---");
        System.out.println(Arrays.toString(new GenericDaoImpl<>(Dayoff.class).getAll().toArray()));
        System.out.println("---");
        System.out.println(Arrays.toString(new GenericDaoImpl<>(DayoffCount.class).getAll().toArray()));
        System.out.println("---");
*/
        manager.getTransaction().begin();
        manager.remove(dayoffCount);
        manager.remove(dayoff);
        manager.remove(type);
        manager.remove(employee);
        manager.remove(department);
        manager.getTransaction().commit();

    }
}
