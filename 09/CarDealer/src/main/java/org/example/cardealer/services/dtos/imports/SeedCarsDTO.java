package org.example.cardealer.services.dtos.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class SeedCarsDTO {

    @XmlElement(name = "car")
    private Set<SeedCarsInfoDTO> cars;

    public SeedCarsDTO() {
        this.cars = new HashSet<>();
    }

    public Set<SeedCarsInfoDTO> getCars() {
        return cars;
    }

    public void setCars(Set<SeedCarsInfoDTO> cars) {
        this.cars = cars;
    }
}
