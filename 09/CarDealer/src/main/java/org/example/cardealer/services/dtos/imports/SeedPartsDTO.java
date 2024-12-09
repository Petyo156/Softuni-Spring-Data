package org.example.cardealer.services.dtos.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class SeedPartsDTO {
    @XmlElement(name = "part")
    private Set<SeedPartsInfoDTO> parts;

    public SeedPartsDTO() {
        this.parts = new HashSet<>();
    }

    public Set<SeedPartsInfoDTO> getParts() {
        return parts;
    }

    public void setParts(Set<SeedPartsInfoDTO> parts) {
        this.parts = parts;
    }
}
