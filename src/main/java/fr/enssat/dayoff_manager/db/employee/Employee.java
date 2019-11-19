package fr.enssat.dayoff_manager.db.employee;

import fr.enssat.dayoff_manager.db.GenericEntity;
import fr.enssat.dayoff_manager.db.department.Department;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Employé (utilisateur)
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Employee extends GenericEntity implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * Prénom
     */
    private String firstName;

    /**
     * Nom
     */
    private String lastName;

    /**
     * Mot de passe (chiffré)
     */
    private String sha256Password;

    /**
     * Adresse
     */
    private String address;

    /**
     * Fonction
     */
    private String position;

    /**
     * Email
     */
    private String email;

    /**
     * Type (employé, chef équipe, employé RH, responsable RH)
     */
    private EmployeeType type;

    /**
     * Service
     */
    private Department department;

    public Employee() {
    }

    public Employee(String firstName,
                    String lastName,
                    String sha256Password,
                    String address,
                    String position,
                    String email,
                    EmployeeType type,
                    Department department) {
        setFirstName(firstName);
        setLastName(lastName);
        setSha256Password(sha256Password);
        setAddress(address);
        setPosition(position);
        setEmail(email);
        setType(type);
        setDepartment(department);
    }

    public static Employee createEmpty() {
        return new Employee("", "", "", "", "", "", EmployeeType.CLASSIC, null);
    }

    @Id
    @GeneratedValue
    @Column(nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = Objects.requireNonNull(firstName);
    }

    @Basic
    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = Objects.requireNonNull(lastName);
    }

    @Basic
    @Column(nullable = false)
    public String getSha256Password() {
        return sha256Password;
    }

    public void setSha256Password(String sha256Password) {
        this.sha256Password = Objects.requireNonNull(sha256Password);
    }

    @Basic
    @Column(nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = Objects.requireNonNull(address);
    }

    @Basic
    @Column(nullable = false)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = Objects.requireNonNull(position);
    }

    @Basic
    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Objects.requireNonNull(email);
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public EmployeeType getType() {
        return type;
    }

    public void setType(EmployeeType type) {
        this.type = Objects.requireNonNull(type);
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_department", nullable = false)
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = Objects.requireNonNull(department);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(sha256Password, employee.sha256Password) &&
                Objects.equals(address, employee.address) &&
                Objects.equals(position, employee.position) &&
                Objects.equals(email, employee.email) &&
                type == employee.type &&
                Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, sha256Password, address, position, email, type, department);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + sha256Password + '\'' +
                ", address='" + address + '\'' +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", department=" + department +
                '}';
    }
}
