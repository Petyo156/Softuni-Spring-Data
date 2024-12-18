package org.example.gamestore.data.repositories;

import org.example.gamestore.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
}
