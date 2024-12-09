package org.example.cardealer.services.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.LinkedHashSet;
import java.util.Set;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportCarsDTO {

    @XmlElement(name = "car")
    private LinkedHashSet<ExportCarDTO> cars;

    public ExportCarsDTO(LinkedHashSet<ExportCarDTO> collected) {
        this.cars = collected;
    }

    public ExportCarsDTO() {
        this.cars = new LinkedHashSet<>();
    }

    public LinkedHashSet<ExportCarDTO> getCars() {
        return cars;
    }

    public void setCars(LinkedHashSet<ExportCarDTO> cars) {
        this.cars = cars;
    }
}
