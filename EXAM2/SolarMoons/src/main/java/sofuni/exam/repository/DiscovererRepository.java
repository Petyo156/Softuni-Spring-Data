package sofuni.exam.repository;


import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofuni.exam.models.entity.Discoverer;

import java.util.Optional;

@Repository
public interface DiscovererRepository extends JpaRepository<Discoverer, Long> {

    Optional<Object> getDiscovererByFirstNameAndLastName(@Length(min = 2, max = 20) @NotNull String firstName, @Length(min = 2, max = 20) @NotNull String lastName);
}
