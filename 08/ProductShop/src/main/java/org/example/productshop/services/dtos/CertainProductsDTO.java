package org.example.productshop.services.dtos;

import com.google.gson.annotations.Expose;

public class CertainProductsDTO {
    @Expose
    String productName;
    @Expose
    String price;
    @Expose
    String seller;

    public CertainProductsDTO() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
