package fr.enssat.dayoff_manager;

import fr.enssat.dayoff_manager.db.DaoProvider;
import fr.enssat.dayoff_manager.db.dayoff.Dayoff;
import fr.enssat.dayoff_manager.db.dayoff.DayoffStatus;
import fr.enssat.dayoff_manager.db.dayoff_count.DayoffCount;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.department.Department;
import fr.enssat.dayoff_manager.db.employee.Employee;
import fr.enssat.dayoff_manager.db.employee.EmployeeType;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@SuppressWarnings("deprecation")
public class Utils {

    private Utils() {
    }

    public static String sha256(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No SHA-256 ?");
        }
        byte[] encodedhash = digest.digest(in.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedhash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void fillDBWithSomeData() {
        Department dep0 = new Department("imr1");
        Department dep1 = new Department("imr2");
        DaoProvider.getDepartmentDao().save(dep0);
        DaoProvider.getDepartmentDao().save(dep1);

        DayoffType type0 = new DayoffType("Vacances", 5.0f, false);
        DayoffType type1 = new DayoffType("Maladie", null, false);
        DaoProvider.getDayoffTypeDao().save(type0);
        DaoProvider.getDayoffTypeDao().save(type1);

        Employee classic = new Employee("classicFirstName",
                "classicLastName",
                Utils.sha256("classicPassword"), /*password*/
                "classicAddress",
                "classicPosition",
                "classic@company.fr",
                EmployeeType.CLASSIC,
                dep0);

        Employee boss = new Employee("bossFirstName",
                "bossLastName",
                Utils.sha256("bossPassword"), /*password*/
                "bossAddress",
                "bossPosition",
                "boss@company.fr",
                EmployeeType.BOSS,
                dep0);

        Employee rh = new Employee("rhFirstName",
                "rhLastName",
                Utils.sha256("rhPassword"), /*password*/
                "rhAddress",
                "rhPosition",
                "rh@company.fr",
                EmployeeType.RH,
                dep1);

        Employee rhADMIN = new Employee("rhADMINFirstName",
                "rhADMINLastName",
                Utils.sha256("rhADMINPassword"), /*password*/
                "rhADMINAddress",
                "rhADMINPosition",
                "rhADMIN@company.fr",
                EmployeeType.RH_ADMIN,
                dep1);

        DaoProvider.getEmployeeDao().save(classic);
        DaoProvider.getEmployeeDao().save(boss);
        DaoProvider.getEmployeeDao().save(rh);
        DaoProvider.getEmployeeDao().save(rhADMIN);

        DayoffCount count = new DayoffCount(null, type0, rhADMIN);
        DaoProvider.getDayoffCountDao().save(count);
        Date dateStart = new Date();
        Date dateDebut = new Date(120, 7, 10);
        Date dateFin = new Date(120, 7, 17);
        Date dateValidation = null;
        float nbrDay = 5;
        DayoffStatus status = DayoffStatus.WAITING;
        String commentRH = null;
        String commentEmployee = "Je pars en vacance à Malibu";
        DayoffType type = type0;
        Employee emp = classic;

        Dayoff demande = new Dayoff(dateDebut, dateFin, dateStart, dateValidation, nbrDay, status, commentRH, commentEmployee, type, emp);

        DaoProvider.getDayoffDao().save(demande);
        Date startDate = resetTimeAttributes(new Date());
        Date endDate = resetTimeAttributes(new Date());
        endDate.setDate(endDate.getDate() + 2);
        Date creationDate = new Date();
        creationDate.setDate(creationDate.getDate() - 7);

        endDate.setHours(12);
        Dayoff dayoff1 = new Dayoff(startDate, endDate, creationDate, null, 2.0f, DayoffStatus.ACCEPTED, null , "Vacances1", type1, rhADMIN);

        endDate = resetTimeAttributes(new Date());
        endDate.setDate(endDate.getDate() + 4);
        Dayoff dayoff2 = new Dayoff(startDate, endDate, creationDate, null, 2.0f, DayoffStatus.WAITING, null, "Vacances2", type1, rh);

        endDate = resetTimeAttributes(new Date());
        endDate.setDate(endDate.getDate() + 6);
        Dayoff dayoff3 = new Dayoff(startDate, endDate, creationDate, null, 2.0f, DayoffStatus.WAITING, null, "Vacances3", type0, boss);

        endDate = resetTimeAttributes(new Date());
        endDate.setDate(endDate.getDate() + 8);
        Dayoff dayoff4 = new Dayoff(startDate, endDate, creationDate, null, 2.0f, DayoffStatus.WAITING, null, "Vacance4", type0, classic);

        DaoProvider.getDayoffDao().save(dayoff1);
        DaoProvider.getDayoffDao().save(dayoff2);
        DaoProvider.getDayoffDao().save(dayoff3);
        DaoProvider.getDayoffDao().save(dayoff4);
    }

    private static Date resetTimeAttributes(Date in) {
        in.setHours(0);
        in.setMinutes(0);
        in.setSeconds(0);
        return in;
    }
}
