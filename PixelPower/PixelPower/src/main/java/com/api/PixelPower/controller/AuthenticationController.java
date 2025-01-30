package com.api.PixelPower.controller;

import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.UserResponseDTO;
import com.api.PixelPower.service.serviceInt.UserServiceInt;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")

public class AuthenticationController {
    private final UserServiceInt userService;

    public AuthenticationController(UserServiceInt userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserDTO userDTO) {
        UserResponseDTO userResponse = userService.register(userDTO);

        // Create a response object with a success message and user data
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User created successfully");
        response.put("user", userResponse);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
