package fr.irvineflth.EcoFleet.dto;

import fr.irvineflth.EcoFleet.domain.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {
    private Long id;
    private String registrationNumber;
    private VehicleType type;
    private String brand;
    private String model;
    private Integer autonomyKm;
    private Integer currentKm;
    private LocalDate lastMaintenanceDate;
    private boolean available;
}
