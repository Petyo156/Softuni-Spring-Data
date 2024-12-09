package org.example.cardealer.services.dtos.imports;


import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class SeedCarsInfoDTO {
    @XmlElement(name = "travelled-distance")
    private Long travelledDistance;
    @XmlElement
    private String make;
    @XmlElement
    private String model;

    public SeedCarsInfoDTO() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}
