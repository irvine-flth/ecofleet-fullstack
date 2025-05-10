package fr.irvineflth.EcoFleet.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.irvineflth.EcoFleet.domain.enums.VehicleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private String brand;
    private String model;
    private Integer autonomyKm;
    private Integer currentKm;

    private LocalDate lastMaintenanceDate;

    @Column(name = "available")
    private boolean available = true;
}
