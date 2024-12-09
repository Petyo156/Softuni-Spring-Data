package org.example.productshop.services.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

public class CategorySeedDTO {
    @Expose
    @Length(min = 3, max = 15)
    private String name;

    public CategorySeedDTO() {
    }

    public CategorySeedDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
