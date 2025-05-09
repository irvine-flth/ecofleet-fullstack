package fr.irvineflth.EcoFleet.unit.mapper;

import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.domain.enums.VehicleType;
import fr.irvineflth.EcoFleet.dto.VehicleDto;
import fr.irvineflth.EcoFleet.mapper.VehicleMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class VehicleMapperTest {

    private final VehicleMapper mapper = VehicleMapper.INSTANCE;

    @Test
    void shouldMapToDto() {
        Vehicle entity = new Vehicle(1L, "EV-123", VehicleType.CAR, "Renault", "Zoé", 250, 10000, LocalDate.now(), true);
        VehicleDto dto = this.mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getRegistrationNumber()).isEqualTo("EV-123");
    }

    @Test
    void shouldMapToEntity() {
        VehicleDto dto = new VehicleDto(1L, "EV-123", VehicleType.CAR, "Renault", "Zoé", 250, 10000, LocalDate.now(), true);
        Vehicle entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getBrand()).isEqualTo("Renault");
    }
}
