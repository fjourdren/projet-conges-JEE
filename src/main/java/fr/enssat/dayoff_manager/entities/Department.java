package fr.enssat.dayoff_manager.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Service
 */
@Entity
public class Department implements Serializable {

    /**
     * ID
     */
    private int id;

    /**
     * Nom
     */
    private String name;

    public Department() {
    }

    public Department(String name) {
        setName(name);
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
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
