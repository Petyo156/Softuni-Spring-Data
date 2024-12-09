package org.example.productshop.services.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Set;

public class CertainUsersDTO {
   @Expose
    private String firstName;
   @Expose
   private String lastName;
   @Expose
   private Set<ProductsSoldDTO> soldProducts;

    public CertainUsersDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<ProductsSoldDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<ProductsSoldDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
