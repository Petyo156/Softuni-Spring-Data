package softuni.exam.repository;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Volcano;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolcanoRepository extends JpaRepository<Volcano, Long> {
    Optional<Volcano> findVolcanoByName(String name);

    boolean existsByName(String name);

    List<Volcano> findAllByElevationGreaterThanAndIsActiveAndLastEruptionIsNotNullOrderByElevationDesc(Integer elevation, boolean active);
}
