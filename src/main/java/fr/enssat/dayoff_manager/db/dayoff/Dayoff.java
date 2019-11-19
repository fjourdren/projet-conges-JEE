package fr.enssat.dayoff_manager.db.dayoff;

import fr.enssat.dayoff_manager.db.GenericEntity;
import fr.enssat.dayoff_manager.db.dayoff_type.DayoffType;
import fr.enssat.dayoff_manager.db.employee.Employee;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * (Demande de) congés
 */
@Entity
public class Dayoff extends GenericEntity implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * Date de début
     */
    private java.util.Date dateStart;

    /**
     * Date de fin
     */
    private java.util.Date dateEnd;

    /**
     * Date de création
     */
    private java.util.Date dateCreation;

    /**
     * Date de validation
     */
    private java.util.Date dateValidation;

    /**
     * Nombre de jours ouvrés
     */
    private float nbDays;

    /**
     * Statut (en attente, accepté, refusé)
     */
    private DayoffStatus status;

    /**
     * Commentaire RH
     */
    private String commentRH;

    /**
     * Commentaire employé (motif)
     */
    private String commentEmployee;

    /**
     * Type du congés
     */
    private DayoffType type;

    /**
     * Employé
     */
    private Employee employee;

    public Dayoff() {
    }

    public Dayoff(Date dateStart,
                  Date dateEnd,
                  Date dateCreation,
                  Date dateValidation,
                  float nbDays,
                  DayoffStatus status,
                  String commentRH,
                  String commentEmployee,
                  DayoffType type,
                  Employee employee) {
        setDateStart(dateStart);
        setDateEnd(dateEnd);
        setDateCreation(dateCreation);
        setDateValidation(dateValidation);
        setNbDays(nbDays);
        setStatus(status);
        setCommentRH(commentRH);
        setCommentEmployee(commentEmployee);
        setType(type);
        setEmployee(employee);
    }

    @Id
    @GeneratedValue
    @Column(nullable = false)
    public Long getId() {
        return id;
    }

    @Deprecated
    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = Objects.requireNonNull(dateStart);
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = Objects.requireNonNull(dateEnd);
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = Objects.requireNonNull(dateCreation);
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }

    @Basic
    @Column(nullable = false)
    public float getNbDays() {
        return nbDays;
    }

    public void setNbDays(float nbDays) {
        if (nbDays < 0.5f) throw new IllegalArgumentException("nbDays must be 0.5 or more");
        if (nbDays % 0.5f != 0) throw new IllegalArgumentException("nbDays must be a multiple of 0.5");
        this.nbDays = nbDays;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    public DayoffStatus getStatus() {
        return status;
    }

    public void setStatus(DayoffStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    @Basic
    public String getCommentRH() {
        return commentRH;
    }

    public void setCommentRH(String commentRH) {
        this.commentRH = commentRH;
    }

    @Basic
    public String getCommentEmployee() {
        return commentEmployee;
    }

    public void setCommentEmployee(String commentEmployee) {
        this.commentEmployee = commentEmployee;
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
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dayoff)) return false;
        Dayoff dayoff = (Dayoff) o;
        return Objects.equals(id, dayoff.id) &&
                Float.compare(dayoff.nbDays, nbDays) == 0 &&
                Objects.equals(dateStart, dayoff.dateStart) &&
                Objects.equals(dateEnd, dayoff.dateEnd) &&
                Objects.equals(dateCreation, dayoff.dateCreation) &&
                Objects.equals(dateValidation, dayoff.dateValidation) &&
                status == dayoff.status &&
                Objects.equals(commentRH, dayoff.commentRH) &&
                Objects.equals(commentEmployee, dayoff.commentEmployee) &&
                Objects.equals(type, dayoff.type) &&
                Objects.equals(employee, dayoff.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateStart, dateEnd, dateCreation, dateValidation, nbDays, status, commentRH, commentEmployee, type, employee);
    }

    @Override
    public String toString() {
        return "Dayoff{" +
                "id=" + id +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", dateCreation=" + dateCreation +
                ", dateValidation=" + dateValidation +
                ", nbDays=" + nbDays +
                ", status=" + status +
                ", commentRH='" + commentRH + '\'' +
                ", commentEmployee='" + commentEmployee + '\'' +
                ", type=" + type +
                ", employee=" + employee +
                '}';
    }


}
