package fr.irvineflth.EcoFleet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceDto {
    private Long id;
    private String type;
    private LocalDate date;
    private int cost;
    private Long vehicleId;
}
