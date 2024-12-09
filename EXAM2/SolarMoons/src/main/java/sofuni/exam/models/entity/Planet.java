package sofuni.exam.models.entity;

import jakarta.persistence.*;
import sofuni.exam.models.enums.Type;

import java.util.Set;

@Entity
@Table(name = "planets")
public class Planet extends BaseEntity {
    @Column(nullable = false)
    private Integer diameter;
    @Column(nullable = false, name = "distance_from_sun")
    private Long distanceFromSun;
    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false, name = "orbital_period")
    private double orbitalPeriod;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "planet", fetch = FetchType.EAGER)
    private Set<Moon> moons;

    public Set<Moon> getMoons() {
        return moons;
    }

    public void setMoons(Set<Moon> moons) {
        this.moons = moons;
    }

    public Planet() {
    }

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }

    public Long getDistanceFromSun() {
        return distanceFromSun;
    }

    public void setDistanceFromSun(Long distanceFromSun) {
        this.distanceFromSun = distanceFromSun;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(double orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}