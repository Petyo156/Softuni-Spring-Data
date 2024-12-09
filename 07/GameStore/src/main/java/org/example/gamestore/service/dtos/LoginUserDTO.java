package org.example.gamestore.service.dtos;

public class LoginUserDTO {

    private String email;

    private String password;

    public LoginUserDTO(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

    public LoginUserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
