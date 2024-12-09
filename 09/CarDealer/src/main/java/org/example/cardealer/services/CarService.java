package org.example.cardealer.services;


import jakarta.xml.bind.JAXBException;

public interface CarService extends BaseService {
    void seedCars() throws JAXBException;
    void exportOrderedCars() throws JAXBException;
}
