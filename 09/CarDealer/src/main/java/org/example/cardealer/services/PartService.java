package org.example.cardealer.services;


import jakarta.xml.bind.JAXBException;

public interface PartService extends BaseService{
    void seedParts() throws JAXBException;
}
