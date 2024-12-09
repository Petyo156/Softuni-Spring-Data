package org.example.productshop.services.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public class ProductSeedDTO {
    @Expose
    @Length(min = 3)
    private String name;

    @Expose
    private BigDecimal price;

    public ProductSeedDTO() {
    }

    public @Length(min = 3) String getName() {
        return name;
    }

    public void setName(@Length(min = 3) String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
