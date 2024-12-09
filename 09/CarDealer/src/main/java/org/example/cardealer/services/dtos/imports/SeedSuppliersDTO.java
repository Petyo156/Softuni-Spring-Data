package org.example.cardealer.services.dtos.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SeedSuppliersDTO {

    @XmlElement(name = "supplier")
    private Set<SeedSuppliersInfoDTO> suppliers;

    public SeedSuppliersDTO() {
        this.suppliers = new HashSet<>();
    }

    public Set<SeedSuppliersInfoDTO> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<SeedSuppliersInfoDTO> suppliers) {
        this.suppliers = suppliers;
    }
}
