package fr.enssat.dayoff_manager.db.employee;

import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffStatus;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.department.Department;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDaoMockImpl implements EmployeeDao {

    private static List<Employee> listEmployee;
    static {
        listEmployee = new ArrayList<Employee>();
        Department department = new Department("imr");
        listEmployee.add(new Employee("Flavien", "Jourdren", "xXdidocheGamingXx", "adr", "pos", "fj@", EmployeeType.BOSS, department));
        listEmployee.add(new Employee("firstClassic", "endClassic", "pass", "adr", "pos", "email1@", EmployeeType.CLASSIC, department));
        listEmployee.add(new Employee("firstRH", "endRH", "pass", "adr", "pos", "email2@", EmployeeType.RH, department));
        listEmployee.add(new Employee("firstRH_Admin", "end_RHAdmin", "pass", "adr", "pos", "email3@", EmployeeType.RH_ADMIN, department));
    }

    @Override
    public void save(Employee entity) {
        //nothing
    }

    @Override
    public void delete(Employee entity) {
        //nothing
    }

    @Override
    public Employee findById(int id) {
        List<Employee> listEmployeeId = listEmployee.stream().filter(x->x.getId() == id).collect(Collectors.toList());
        if(listEmployeeId.size() == 1 ) return listEmployeeId.get(0);
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return listEmployee;
    }

    public boolean login(Employee employee, String email, String password){
        return (employee.getEmail().equals(email) && employee.getPassword().equals(password));
    }

    public boolean createDayoff(Employee employee, DayoffType dayoffType, int nbDays, Date dateStart, Date dateEnd){
        // TODO: décider si la fonction return true ou false si l'insertion est possible ou pas ou bien void
        return false;
    }

    public List<Dayoff> getDayoff(Employee employee){
        // TODO
        return null;
    }

    public int nbDaysUsable(DayoffType dayoffType){
        // TODO
        return 0;
    }

    public boolean modifyDayoffDemand(Dayoff dayoff, Dayoff newDayOff){
        // TODO
        return false;
    }

    public boolean deleteDayoffDemand(Dayoff dayoff){
        // TODO
        return false;
    }

}
