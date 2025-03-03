package com.api.PixelPower.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "upgrade_suggestions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpgradeSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Limiting component is mandatory")
    private String limitingComponent;

    @NotBlank(message = "Suggested upgrade is mandatory")
    private String suggestedUpgrade;

    @ManyToOne
    @JoinColumn(name = "comparison_id", nullable = false)
    private GameComparison gameComparison;
}
