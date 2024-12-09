package org.example.cardealer.persistence.repositories;

import org.example.cardealer.persistence.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    LinkedHashSet<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);
}
