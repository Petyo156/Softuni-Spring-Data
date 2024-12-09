package org.example.entities.sales;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Product product;

    @ManyToOne(optional = false)
    private Customer customer;

    @JoinColumn(name = "store_location")
    @ManyToOne
    private StoreLocation storeLocation;

    @Basic
    private LocalDate saleDate;
}
