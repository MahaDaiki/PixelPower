package com.api.PixelPower.controller;


import com.api.PixelPower.dto.request.PasswordResetRequestDTO;
import com.api.PixelPower.dto.ResetPasswordDTO;
import com.api.PixelPower.service.serviceInt.PasswordResetServiceInt;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetServiceInt passwordResetService;
    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody PasswordResetRequestDTO requestDTO) {
        passwordResetService.sendResetPasswordEmail(requestDTO);
        return ResponseEntity.ok("Password reset link sent successfully!");
    }


    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        passwordResetService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok("Password reset successfully!");
    }
}
