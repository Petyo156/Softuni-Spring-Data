package org.example.cardealer.services;


import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface SupplierService extends BaseService{
    void seedSuppliers() throws JAXBException;
}
