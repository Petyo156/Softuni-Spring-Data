package org.example.cardealer.services.dtos.exports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportCustomersDTO {

    @XmlElement(name = "customer")
    private Set<ExportCustromerDTO> customers;

    public ExportCustomersDTO(Set<ExportCustromerDTO> collected) {
        this.customers = collected;
    }

    public ExportCustomersDTO() {
        this.customers = new HashSet<>();
    }

    public Set<ExportCustromerDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<ExportCustromerDTO> customers) {
        this.customers = customers;
    }
}
