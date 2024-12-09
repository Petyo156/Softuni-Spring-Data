package org.example.productshop.persistence.repositories;

import org.example.productshop.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Set<User> findAllBySoldIsNotNull();

}
