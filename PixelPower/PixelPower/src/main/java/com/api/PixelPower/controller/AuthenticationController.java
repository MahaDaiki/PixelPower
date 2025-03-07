package com.api.PixelPower.controller;

import com.api.PixelPower.dto.response.AuthTokenResponseDTO;
import com.api.PixelPower.dto.request.LoginRequestDTO;
import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.response.UserResponseDTO;
import com.api.PixelPower.service.serviceInt.UserServiceInt;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final UserServiceInt userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture) {

        String profilePicUrl = null;
        try {
            if (profilePicture != null && !profilePicture.isEmpty()) {
                // Create directory if it doesn't exist
                File uploadDir = new File("public/profile_pictures");
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Generate unique filename
                String originalFileName = profilePicture.getOriginalFilename();
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString() + fileExtension;

                // Save file
                Path targetPath = Paths.get("public/profile_pictures").resolve(newFileName);
                Files.copy(profilePicture.getInputStream(), targetPath);

                profilePicUrl = "/profile_pictures/" + newFileName;
            }

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setEmail(email);
            userDTO.setPassword(password);
            userDTO.setProfilePicture(profilePicUrl);

            UserResponseDTO userResponse = userService.register(userDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "User created successfully");
            response.put("user", userResponse);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IOException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to upload profile picture");
            errorResponse.put("error", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }


}
