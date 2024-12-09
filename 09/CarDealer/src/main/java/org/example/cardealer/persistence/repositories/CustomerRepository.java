package org.example.cardealer.persistence.repositories;

import org.example.cardealer.persistence.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    LinkedHashSet<Customer> findAllByOrderByBirthDateAscIsYoungDriverDesc();
}
