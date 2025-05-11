package fr.irvineflth.EcoFleet.unit.mapper;

import fr.irvineflth.EcoFleet.domain.entity.Reservation;
import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.dto.ReservationDto;
import fr.irvineflth.EcoFleet.mapper.ReservationMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationMapperTest {
    private final ReservationMapper mapper = ReservationMapper.INSTANCE;

    @Test
    void shouldMapToDto() {
        Vehicle vehicle = Vehicle.builder()
                .id(42L)
                .registrationNumber("XYZ-123")
                .build();

        Reservation entity = Reservation.builder()
                .id(1L)
                .startDate(LocalDate.of(2025, 6, 1))
                .endDate(LocalDate.of(2025, 6, 3))
                .driverName("John Doe")
                .vehicle(vehicle)
                .build();

        ReservationDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getStartDate()).isEqualTo(LocalDate.of(2025, 6, 1));
        assertThat(dto.getEndDate()).isEqualTo(LocalDate.of(2025, 6, 3));
        assertThat(dto.getDriverName()).isEqualTo("John Doe");
        assertThat(dto.getVehicleId()).isEqualTo(42L);
    }

    @Test
    void shouldMapToEntity() {
        ReservationDto dto = new ReservationDto(null,
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 3),
                "Jane Smith",
                7L);

        Vehicle vehicle = Vehicle.builder()
                .id(7L)
                .registrationNumber("ABC-987")
                .build();

        Reservation entity = mapper.toEntity(dto, vehicle);

        assertThat(entity).isNotNull();
        assertThat(entity.getStartDate()).isEqualTo(LocalDate.of(2025, 6, 1));
        assertThat(entity.getEndDate()).isEqualTo(LocalDate.of(2025, 6, 3));
        assertThat(entity.getDriverName()).isEqualTo("Jane Smith");
        assertThat(entity.getVehicle()).isEqualTo(vehicle);
    }
}
