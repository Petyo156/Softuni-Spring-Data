package org.example.cardealer.services.dtos.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SeedCustomersDTO {

    @XmlElement(name = "customer")
    private Set<SeedCustomersInfoDTO> customers;

    public SeedCustomersDTO() {
        this.customers = new HashSet<>();
    }

    public Set<SeedCustomersInfoDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<SeedCustomersInfoDTO> customers) {
        this.customers = customers;
    }
}