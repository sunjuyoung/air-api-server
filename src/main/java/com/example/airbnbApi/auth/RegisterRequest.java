package com.example.airbnbApi.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class RegisterRequest {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 4)
    private String password;

    private String imagePath;


    public RegisterRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public RegisterRequest(String email, String name, String password, String imagePath) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.imagePath = imagePath;
    }
}
