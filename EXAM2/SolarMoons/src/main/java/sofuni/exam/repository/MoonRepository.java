package sofuni.exam.repository;


import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sofuni.exam.models.entity.Moon;
import sofuni.exam.models.enums.Type;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoonRepository  extends JpaRepository<Moon, Long> {

    List<Moon> findAllByRadiusBetweenAndPlanet_TypeOrderByName(double lower, double upper, Type type);
    Optional<Moon> findMoonByName(@NotNull @Length(min = 2, max = 10) String name);
}
