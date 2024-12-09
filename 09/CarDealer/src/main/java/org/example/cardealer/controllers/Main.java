package org.example.cardealer.controllers;

import org.example.cardealer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Main implements CommandLineRunner {
    private final CarService carService;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CustomerService customerService;
    private final SaleService saleService;

    @Autowired
    public Main(CarService carService, SupplierService supplierService, PartService partService, CustomerService customerService, SaleService saleService) {
        this.carService = carService;
        this.supplierService = supplierService;
        this.partService = partService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(!supplierService.isImported()) {
            supplierService.seedSuppliers();
        }
        if(!partService.isImported()) {
            partService.seedParts();
        }
        if(!carService.isImported()) {
            carService.seedCars();
        }
        if(!customerService.isImported()) {
            customerService.seedCustomers();
        }
        if(!saleService.isImported()) {
            saleService.seedData();
        }

        customerService.exportOrderedCustomers();
        carService.exportOrderedCars();
    }
}
