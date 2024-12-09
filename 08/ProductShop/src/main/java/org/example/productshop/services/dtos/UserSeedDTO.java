package org.example.productshop.services.dtos;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;

public class UserSeedDTO {
    @Expose
    private String firstName;

    @Expose
    @NotNull
    private String lastName;

    @Expose
    private String age;

    public UserSeedDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public @NotNull String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
