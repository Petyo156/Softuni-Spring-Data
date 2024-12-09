package sofuni.exam.models.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "moons")
public class Moon extends BaseEntity {
    @Column(nullable = false)
    private LocalDate discovered;
    @Column
    private Integer distanceFromPlanet;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private double radius;

    @ManyToOne(optional = false)
    @JoinColumn(name = "planet_id",
            referencedColumnName = "id")
    private Planet planet;

    @ManyToOne(optional = false)
    @JoinColumn(name = "discoverer_id",
            referencedColumnName = "id")
    private Discoverer discoverer;

    public LocalDate getDiscovered() {
        return discovered;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Discoverer getDiscoverer() {
        return discoverer;
    }

    public void setDiscoverer(Discoverer discoverer) {
        this.discoverer = discoverer;
    }

    public Moon() {
    }

    public LocalDate isDiscovered() {
        return discovered;
    }

    public void setDiscovered(LocalDate discovered) {
        this.discovered = discovered;
    }

    public Integer getDistanceFromPlanet() {
        return distanceFromPlanet;
    }

    public void setDistanceFromPlanet(Integer distanceFromPlanet) {
        this.distanceFromPlanet = distanceFromPlanet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
