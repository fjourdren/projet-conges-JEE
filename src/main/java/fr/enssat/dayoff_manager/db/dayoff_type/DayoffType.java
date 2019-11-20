package fr.enssat.dayoff_manager.db.dayoff_type;

import fr.enssat.dayoff_manager.db.GenericEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Type de congés
 */
@Entity
public class DayoffType extends GenericEntity implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * Nom
     */
    private String name;

    /**
     * Nombre de jours annuels par défaut pour tous les employés (null si pas de limitation)
     */
    private Float defaultNbDays;

    /**
     * Si true, le type est marqué comme supprimé et ne doit plus être utilisé
     */
    private boolean deleted;

    public DayoffType() {
    }

    public DayoffType(String name, Float defaultNbDays, boolean deleted) {
        setName(name);
        setDefaultNbDays(defaultNbDays);
        setDeleted(deleted);
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
    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    public Float getDefaultNbDays() {
        return defaultNbDays;
    }

    public void setDefaultNbDays(Float defaultNbDays) {
        if (defaultNbDays != null) {
            if (defaultNbDays < 0f) throw new IllegalArgumentException("defaultNbDays must be 0 or more, or null");
            if (defaultNbDays % 0.5f != 0)
                throw new IllegalArgumentException("defaultNbDays must be a multiple of 0.5");
        }
        this.defaultNbDays = defaultNbDays;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayoffType)) return false;
        DayoffType that = (DayoffType) o;
        return Objects.equals(id, that.id) &&
                defaultNbDays.equals(that.defaultNbDays) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, defaultNbDays);
    }

    @Override
    public String toString() {
        return "DayoffType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", defaultNbDays=" + defaultNbDays +
                '}';
    }
}
