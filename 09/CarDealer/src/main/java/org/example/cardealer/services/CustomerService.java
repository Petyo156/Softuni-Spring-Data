package org.example.cardealer.services;


import jakarta.xml.bind.JAXBException;
import org.example.cardealer.persistence.entities.Customer;

import java.io.IOException;

public interface CustomerService extends BaseService {
    void seedCustomers() throws JAXBException;
    void exportOrderedCustomers() throws JAXBException;
}
