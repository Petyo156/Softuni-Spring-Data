package sofuni.exam.repository;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofuni.exam.models.entity.Planet;

import java.util.Optional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {


    Optional<Object> getPlanetByName(@Length(min = 3, max = 20) @NotNull String name);
}
