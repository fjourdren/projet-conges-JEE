package fr.enssat.dayoff_manager.db.dayoff_type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Type de congés
 */
@Entity
public class DayoffType implements Serializable {

    /**
     * ID
     */
    private int id;

    /**
     * Nom
     */
    private String name;

    /**
     * Nombre de jours annuels par défaut pour tous les employés (null si pas de limitation)
     */
    private Float defaultNbDays;

    public DayoffType() {
    }

    public DayoffType(String name, Float defaultNbDays) {
        setName(name);
        setDefaultNbDays(defaultNbDays);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayoffType)) return false;
        DayoffType that = (DayoffType) o;
        return id == that.id &&
                defaultNbDays == that.defaultNbDays &&
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
