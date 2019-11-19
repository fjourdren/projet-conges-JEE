package fr.enssat.dayoff_manager.db.employee;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCount;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.department.Department;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDaoMockImpl implements EmployeeDao {

    private List<Employee> listEmployee = new ArrayList<>();
    private int nextID = -1;

    public EmployeeDaoMockImpl() {
        Department dep0 = new Department("imr1");
        DaoProvider.getDepartmentDao().save(dep0);

        Employee boss = new Employee("Flavien", "Jourdren", "password", "adr", "pos", "fj@gmail.com", EmployeeType.RH_ADMIN, dep0);
        boss.setId(++nextID);

        Employee classic = new Employee("firstClassic", "endClassic", "pass", "adr", "pos", "email1@", EmployeeType.CLASSIC, dep0);
        classic.setId(++nextID);

        Employee rh = new Employee("firstRH", "endRH", "pass", "adr", "pos", "email2@", EmployeeType.RH, dep0);
        rh.setId(++nextID);

        Employee rh_admin = new Employee("firstRH_Admin", "end_RHAdmin", "pass", "adr", "pos", "email3@", EmployeeType.RH_ADMIN, dep0);
        rh_admin.setId(++nextID);

        listEmployee.add(boss);
        listEmployee.add(classic);
        listEmployee.add(rh);
        listEmployee.add(rh_admin);
    }

    @Override
    public void save(Employee entity) {
    	if(entity.getId() == -1) {
    		entity.setId(++nextID);
    	} else {
    		Employee lastEmployee = this.findById(entity.getId());
    		listEmployee.remove(lastEmployee);
    	}
    	
    	listEmployee.add(entity);
        
    }

    @Override
    public void delete(Employee entity) {
        listEmployee.remove(entity);
    }

    @Override
    public Employee findById(int id) {
        List<Employee> listEmployeeId = listEmployee.stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if (listEmployeeId.size() == 1) return listEmployeeId.get(0);
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return Collections.unmodifiableList(listEmployee);
    }

    @Override
    public Employee login(String email, String password) {
        for (Employee employee : listEmployee) {
            if (employee.getEmail().equals(email) && employee.getPassword().equals(password)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public List<Dayoff> getDayOffs(Employee employee) {
        List<Dayoff> dayoffs = new ArrayList<>();

        for (Dayoff dayoff : DaoProvider.getDayoffDao().getAll()) {
            if (dayoff.getEmployee().equals(employee)) {
                dayoffs.add(dayoff);
            }
        }

        return dayoffs;
    }

    @Override
    public Float nbDaysUsable(Employee employee, DayoffType dayoffType) {
        for (DayoffCount dayoffCount : DaoProvider.getDayoffCountDao().getAll()) {
            if (dayoffCount.getType() == dayoffType && dayoffCount.getEmployee().equals(employee)) {
                return dayoffCount.getNbDays();
            }
        }

        DayoffCount dayoffCount = new DayoffCount();
        dayoffCount.setType(dayoffType);
        dayoffCount.setNbDays(dayoffType.getDefaultNbDays());
        dayoffCount.setEmployee(employee);
        dayoffCount.setType(dayoffType);

        DaoProvider.getDayoffCountDao().save(dayoffCount);
        return dayoffCount.getNbDays();
    }

}
