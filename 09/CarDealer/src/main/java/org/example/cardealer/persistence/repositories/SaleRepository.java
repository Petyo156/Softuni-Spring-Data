package org.example.cardealer.persistence.repositories;

import org.example.cardealer.persistence.entities.Car;
import org.example.cardealer.persistence.entities.Customer;
import org.example.cardealer.persistence.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    boolean existsByCarAndCustomer(Car car, Customer customer);
}
