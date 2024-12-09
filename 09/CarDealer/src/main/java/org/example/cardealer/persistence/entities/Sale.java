package org.example.cardealer.persistence.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    @Column()
    private double discount;

    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id", unique = true)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public Sale() {
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return this.discount == sale.discount && Objects.equals(this.customer, sale.customer) && Objects.equals(this.car, sale.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discount, car, customer);
    }
}
