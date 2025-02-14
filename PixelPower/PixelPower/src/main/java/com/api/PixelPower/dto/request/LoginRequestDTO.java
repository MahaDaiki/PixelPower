package com.api.PixelPower.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")

    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
