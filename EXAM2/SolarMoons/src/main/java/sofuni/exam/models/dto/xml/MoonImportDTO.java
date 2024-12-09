package sofuni.exam.models.dto.xml;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.Length;

@XmlRootElement(name = "moon")
@XmlAccessorType(XmlAccessType.FIELD)
public class MoonImportDTO {
    @XmlElement
    @NotNull
    @Length(min = 2, max = 10)
    private String name;
    @XmlElement
    @NotNull
    private String discovered;
    @XmlElement(name = "distance_from_planet")
    private Long distanceFromPlanet;
    @XmlElement
    @NotNull
    @Positive
    private double radius;
    @XmlElement(name = "discoverer_id")
    @NotNull
    private Long discovererId;
    @XmlElement(name = "planet_id")
    @NotNull
    private Long planetId;

    public MoonImportDTO() {
    }

    public @NotNull @Length(min = 2, max = 10) String getName() {
        return name;
    }

    public void setName(@NotNull @Length(min = 2, max = 10) String name) {
        this.name = name;
    }

    public @NotNull String getDiscovered() {
        return discovered;
    }

    public void setDiscovered(@NotNull String discovered) {
        this.discovered = discovered;
    }

    public Long getDistanceFromPlanet() {
        return distanceFromPlanet;
    }

    public void setDistanceFromPlanet(Long distanceFromPlanet) {
        this.distanceFromPlanet = distanceFromPlanet;
    }

    @NotNull
    public double getRadius() {
        return radius;
    }

    public void setRadius(@NotNull double radius) {
        this.radius = radius;
    }

    public Long getDiscovererId() {
        return discovererId;
    }

    public void setDiscovererId(Long discovererId) {
        this.discovererId = discovererId;
    }

    public Long getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Long planetId) {
        this.planetId = planetId;
    }
}
