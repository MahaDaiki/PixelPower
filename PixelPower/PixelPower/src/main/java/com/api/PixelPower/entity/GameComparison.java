package com.api.PixelPower.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "game_comparisons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameComparison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Game name is mandatory")
    private String gameName;

    @NotNull(message = "CPU Compatibility status is mandatory")
    private Boolean cpuCompatible;

    @NotNull(message = "GPU Compatibility status is mandatory")
    private Boolean gpuCompatible;

    @NotNull(message = "RAM Compatibility status is mandatory")
    private Boolean ramCompatible;

    @NotNull(message = "Storage Compatibility status is mandatory")
    private Boolean storageCompatible;

    @NotNull(message = "Compatibility status is mandatory")
    private Boolean compatible;

    @Pattern(
            regexp = "^[0-9]+\\s*FPS$",
            message = "Estimated FPS must be a number followed by 'FPS' (e.g., '60FPS')"
    )
    private String estimatedFpsLow;

    @Pattern(
            regexp = "^[0-9]+\\s*FPS$",
            message = "Estimated FPS must be a number followed by 'FPS' (e.g., '60FPS')"
    )
    private String estimatedFpsMedium;

    @Pattern(
            regexp = "^[0-9]+\\s*FPS$",
            message = "Estimated FPS must be a number followed by 'FPS' (e.g., '60FPS')"
    )
    private String estimatedFpsHigh;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private LocalDateTime checkedAt;

    @ManyToOne
    @JoinColumn(name = "configuration_id", nullable = false)
    private Configuration configuration;

    @PrePersist
    private void onCreate() {
        this.checkedAt = LocalDateTime.now();
    }
}
