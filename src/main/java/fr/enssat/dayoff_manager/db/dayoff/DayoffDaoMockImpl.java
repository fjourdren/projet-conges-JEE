package fr.enssat.dayoff_manager.db.dayoff;

import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.department.Department;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DayoffDaoMockImpl implements DayoffDao {

    private static List<Dayoff> listDayoff;
    static {
        listDayoff = new ArrayList<Dayoff>();

        Department department = new Department("imr");
        Employee employeeTest = new Employee("firstClassic", "endClassic", "pass", "adr", "pos", "email1@", EmployeeType.CLASSIC, department);
        DayoffType type = new DayoffType("type", 5f);
        listDayoff.add(new Dayoff(new Date(), new Date(), new Date(), new Date(), 10, DayoffStatus.WAITING, "comment", "aie", type, employeeTest));
        listDayoff.add(new Dayoff(new Date(), new Date(), new Date(), new Date(), 20, DayoffStatus.WAITING, "comment", "aie", type, employeeTest));
        listDayoff.add(new Dayoff(new Date(), new Date(), new Date(), new Date(), 30, DayoffStatus.WAITING, "comment", "aie", type, employeeTest));
        listDayoff.add(new Dayoff(new Date(), new Date(), new Date(), new Date(), 40, DayoffStatus.WAITING, "comment", "aie", type, employeeTest));
    }

    @Override
    public void save(Dayoff entity) {
        //nothing
    }

    @Override
    public void delete(Dayoff entity) {
        //nothing
    }

    @Override
    public Dayoff findById(int id) {
        List<Dayoff> listDayId = listDayoff.stream().filter(x->x.getId() == id).collect(Collectors.toList());
        if(listDayId.size() == 1 ) return listDayId.get(0);
        return null;
    }

    @Override
    public List<Dayoff> getAll() {
        return listDayoff;
    }

    public void validate(String comment, Dayoff df){
        df.setCommentRH(comment);
        df.setStatus(DayoffStatus.ACCEPTED);
    }

    public void refuse(String comment, Dayoff df){
        df.setCommentRH(comment);
        df.setStatus(DayoffStatus.REFUSED);
    }

    public List<Dayoff> getEmployeeDayoff(Employee employee){
        //TODO
        return null;
    }

    public List<Dayoff> getDepartmentDayoff(Department department){
        //TODO
        return null;
    }


}
