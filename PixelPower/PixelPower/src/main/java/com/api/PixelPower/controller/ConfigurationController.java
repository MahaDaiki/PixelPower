package com.api.PixelPower.controller;

import com.api.PixelPower.dto.ConfigurationDTO;
import com.api.PixelPower.dto.ConfigurationResponseDTO;
import com.api.PixelPower.service.serviceInt.ConfigurationServiceInt;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configurations")
@AllArgsConstructor
public class ConfigurationController {
    private final ConfigurationServiceInt configurationService;
    @PostMapping
    public ResponseEntity<ConfigurationResponseDTO> createConfiguration(@RequestBody ConfigurationDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            System.out.println("Authenticated user: " + authentication.getName());
        } else {
            System.out.println("No authenticated user found.");
        }
        return ResponseEntity.ok(configurationService.createConfiguration(dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationResponseDTO> getConfigurationById(@PathVariable Long id) {
        return ResponseEntity.ok(configurationService.getConfigurationById(id));
    }

    @GetMapping
    public ResponseEntity<List<ConfigurationResponseDTO>> getConfigurationsByAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            System.out.println("Authenticated user: " + auth.getName());
        } else {
            System.out.println("No authenticated user found");
        }

        return ResponseEntity.ok(configurationService.getConfigurationsByAuthenticatedUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfigurationResponseDTO> updateConfiguration(@PathVariable Long id, @RequestBody ConfigurationDTO dto) {
        return ResponseEntity.ok(configurationService.updateConfiguration(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfiguration(@PathVariable Long id) {
        configurationService.deleteConfiguration(id);
        return ResponseEntity.noContent().build();
    }
}
