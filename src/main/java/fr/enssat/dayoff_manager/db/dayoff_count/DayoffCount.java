package fr.enssat.dayoff_manager.db.dayoff_count;

import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.employee.Employee;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Nombre de jours de congés restants pour un type et un employé
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id_type", "id_employee"})})
public class DayoffCount implements Serializable {

    /**
     * ID
     */
    private int id;

    /**
     * Nombre de jours annuels restants (null si pas de limitation)
     */
    private Float nbDays;

    /**
     * Type de congés
     */
    private DayoffType type;

    /**
     * Employé concerné
     */
    private Employee employee;

    public DayoffCount() {
    }

    public DayoffCount(Float nbDays, DayoffType type, Employee employee) {
        setNbDays(nbDays);
        setType(type);
        setEmployee(employee);
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
    public Float getNbDays() {
        return nbDays;
    }

    public void setNbDays(Float nbDays) {
        if (nbDays != null) {
            if (nbDays < 0f) throw new IllegalArgumentException("nbDays must be 0 or more, or null");
            if (nbDays % 0.5f != 0) throw new IllegalArgumentException("nbDays must be a multiple of 0.5");
        }
        this.nbDays = nbDays;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type", nullable = false)
    public DayoffType getType() {
        return type;
    }

    public void setType(DayoffType type) {
        this.type = Objects.requireNonNull(type);
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee", nullable = false)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = Objects.requireNonNull(employee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayoffCount)) return false;
        DayoffCount that = (DayoffCount) o;
        return id == that.id &&
                Objects.equals(nbDays, that.nbDays) &&
                Objects.equals(type, that.type) &&
                Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nbDays, type, employee);
    }

    @Override
    public String toString() {
        return "DayoffCount{" +
                "id=" + id +
                ", nbDays=" + nbDays +
                ", type=" + type +
                ", employee=" + employee +
                '}';
    }
}
