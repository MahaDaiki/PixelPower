package com.api.PixelPower.controller;

import com.api.PixelPower.dto.AuthTokenResponseDTO;
import com.api.PixelPower.dto.LoginRequestDTO;
import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.UserResponseDTO;
import com.api.PixelPower.service.serviceInt.UserServiceInt;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AuthenticationController {
    private final UserServiceInt userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserDTO userDTO) {
        UserResponseDTO userResponse = userService.register(userDTO);


        Map<String, Object> response = new HashMap<>();
        response.put("message", "User created successfully");
        response.put("user", userResponse);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }


}
