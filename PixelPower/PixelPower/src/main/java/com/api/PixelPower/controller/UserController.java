package com.api.PixelPower.controller;

import com.api.PixelPower.dto.UserDTO;
import com.api.PixelPower.dto.response.UserResponseDTO;
import com.api.PixelPower.service.serviceInt.UserServiceInt;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceInt userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser() {
        UserResponseDTO user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> updateAuthenticatedUser(@Valid @RequestBody UserDTO userDTO) {
        UserResponseDTO updatedUser = userService.updateAuthenticatedUser(userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/me/profile-picture")
    public ResponseEntity<UserResponseDTO> updateProfilePicture(@RequestParam("profilepicture") MultipartFile file) {
        UserResponseDTO updatedUser = userService.updateProfilePicture(file);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/me/password")
    public ResponseEntity<UserResponseDTO> updateAuthenticatedPassword(@RequestBody String newPassword) {
        UserResponseDTO updatedUser = userService.updateAuthenticatedPassword(newPassword);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
