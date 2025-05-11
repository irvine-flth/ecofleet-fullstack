package fr.irvineflth.EcoFleet.unit.mapper;

import fr.irvineflth.EcoFleet.domain.entity.Maintenance;
import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.domain.enums.VehicleType;
import fr.irvineflth.EcoFleet.dto.MaintenanceDto;
import fr.irvineflth.EcoFleet.dto.VehicleDto;
import fr.irvineflth.EcoFleet.mapper.MaintenanceMapper;
import fr.irvineflth.EcoFleet.mapper.VehicleMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class MaintenanceMapperTest {

    private final MaintenanceMapper mapper = MaintenanceMapper.INSTANCE;

    @Test
    void shouldMapToDto() {
        Vehicle vehicle = Vehicle.builder().id(1L).model("Zoe").build();
        Maintenance m = Maintenance.builder().id(10L).type("Freins").date(LocalDate.now()).cost(300).vehicle(vehicle).build();

        MaintenanceDto dto = mapper.toDto(m);

        assertThat(dto.getVehicleId()).isEqualTo(1L);
        assertThat(dto.getType()).isEqualTo("Freins");
    }

    @Test
    void shouldMapToEntity() {
        MaintenanceDto dto = MaintenanceDto.builder()
                .id(42L)
                .type("Pneus")
                .date(LocalDate.of(2024, 5, 1))
                .cost(250)
                .vehicleId(99L)
                .build();

        Vehicle vehicle = Vehicle.builder()
                .id(99L)
                .model("Peugeot e-208")
                .build();

        Maintenance entity = mapper.toEntity(dto, vehicle);

        assertThat(entity.getType()).isEqualTo("Pneus");
        assertThat(entity.getCost()).isEqualTo(250);
        assertThat(entity.getVehicle().getId()).isEqualTo(99L);
    }
}
