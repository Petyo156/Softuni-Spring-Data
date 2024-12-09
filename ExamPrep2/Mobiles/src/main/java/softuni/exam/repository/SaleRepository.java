package softuni.exam.repository;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Device;
import softuni.exam.models.entity.Sale;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    Optional<Object> findByNumber(String number);
    Optional<Sale> findById(Long id);
}
