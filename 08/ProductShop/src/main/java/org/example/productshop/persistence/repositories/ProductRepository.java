package org.example.productshop.persistence.repositories;

import org.example.productshop.persistence.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Set<Product> getProductsByPriceBetweenAndBuyerIsNull(BigDecimal min, BigDecimal max);
}
