package com.api.PixelPower.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "configurations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;


    @Pattern(
            regexp = "^(Intel|AMD)\\s+[a-zA-Z0-9\\s\\-\\+]+$",
            message = "CPU must start with 'Intel' or 'AMD', followed by the model name (e.g., 'Intel Core i7-10700K')"
    )
    @NotBlank(message = "CPU is mandatory")
    private String cpu;

    @Pattern(
            regexp = "^(NVIDIA|AMD|Intel)\\s+[a-zA-Z0-9\\s\\-\\+]+$",
            message = "GPU must start with 'NVIDIA', 'AMD', or 'Intel', followed by the model name (e.g., 'NVIDIA RTX 3080')"
    )
    @NotBlank(message = "GPU is mandatory")
    private String gpu;

    @NotBlank(message = "RAM is mandatory")
    @Pattern(
            regexp = "^(4|8|16|32|64|128)GB$",
            message = "RAM must be 4GB, 8GB, 16GB, 32GB, 64GB, or 128GB (e.g., '16GB')"
    )
    private String ram;

    @Pattern(
            regexp = "^(128|256|512|1024|2048)GB$|^(1|2|4|8)TB$",
            message = "Storage must be 128GB, 256GB, 512GB, 1TB, 2TB, 4TB, or 8TB (e.g., '512GB', '2TB')"
    )
    @NotBlank(message = "Storage is mandatory")
    private String storage;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Operating System is mandatory")
    private OperatingSystem os;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "You can only have one Primary Configuration!!")
    private ConfigStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameComparison> gameComparisons = new ArrayList<>();

    @OneToMany(mappedBy = "configuration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UpgradeSuggestion> upgradeSuggestions = new ArrayList<>();


    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
