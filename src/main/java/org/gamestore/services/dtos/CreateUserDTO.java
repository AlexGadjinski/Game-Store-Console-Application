package org.gamestore.services.dtos;

import jakarta.validation.constraints.Pattern;

public class CreateUserDTO {
    @Pattern(regexp = "^\\w+@\\w+\\.\\w+$", message = "Invalid email!")
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$", message = "Invalid password!")
    private String password;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$", message = "Invalid confirmed password!")
    private String confirmedPassword;
    private String fullName;

    public CreateUserDTO() {}

    public CreateUserDTO(String email, String password, String confirmedPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
        this.fullName = fullName;
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

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
