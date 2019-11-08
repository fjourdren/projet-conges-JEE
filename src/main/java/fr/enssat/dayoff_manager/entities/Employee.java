package fr.enssat.dayoff_manager.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Employé (utilisateur)
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName"})})
public class Employee implements Serializable {

    /**
     * ID
     */
    private int id;

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
    private String password;

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
                    String password,
                    String address,
                    String position,
                    String email,
                    EmployeeType type,
                    Department department) {
        setFirstName(firstName);
        setLastName(lastName);
        setPassword(password);
        setAddress(address);
        setPosition(position);
        setEmail(email);
        setType(type);
        setDepartment(department);
    }

    @Id
    @GeneratedValue
    @Column(nullable = false)
    public int getId() {
        return id;
    }

    @Deprecated
    public void setId(int id) {
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Objects.requireNonNull(password);
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
        return id == employee.id &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(password, employee.password) &&
                Objects.equals(address, employee.address) &&
                Objects.equals(position, employee.position) &&
                Objects.equals(email, employee.email) &&
                type == employee.type &&
                Objects.equals(department, employee.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, password, address, position, email, type, department);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", department=" + department +
                '}';
    }
}
