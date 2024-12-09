package org.example.cardealer.services.impls;

import org.example.cardealer.persistence.entities.Car;
import org.example.cardealer.persistence.entities.Customer;
import org.example.cardealer.persistence.entities.Sale;
import org.example.cardealer.persistence.repositories.CarRepository;
import org.example.cardealer.persistence.repositories.CustomerRepository;
import org.example.cardealer.persistence.repositories.SaleRepository;
import org.example.cardealer.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;


    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CustomerRepository customerRepository, CarRepository carRepository) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    @Override
    public boolean isImported() {
        return this.saleRepository.count() > 0;
    }

    private List<Customer> allCustomers;
    private List<Car> allCars;

    @Override
    public void seedData() {
        allCustomers = customerRepository.findAll();
        allCars = carRepository.findAll();

        List<Double> numbers = List.of(0.0, 0.05, 0.1, 0.15, 0.20, 0.30, 0.40, 0.50);
        Set<Car> usedCars = new HashSet<>();

        for (int i = 0; i < 35; i++) {
            Car car = getRandomCar(usedCars);
            Sale sale = new Sale();
            sale.setDiscount(numbers.get(ThreadLocalRandom.current().nextInt(numbers.size())));
            sale.setCar(car);
            sale.setCustomer(getRandomCustomer());
            saleRepository.saveAndFlush(sale);
            usedCars.add(car);
        }
    }

    private Customer getRandomCustomer() {
        return allCustomers.get(ThreadLocalRandom.current().nextInt(allCustomers.size()));
    }

    private Car getRandomCar(Set<Car> usedCars) {
        Car car;
        do {
            car = allCars.get(ThreadLocalRandom.current().nextInt(allCars.size()));
        } while (usedCars.contains(car));
        return car;
    }
}
