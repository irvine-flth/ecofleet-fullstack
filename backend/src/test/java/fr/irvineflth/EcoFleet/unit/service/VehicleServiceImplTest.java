package fr.irvineflth.EcoFleet.unit.service;

import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.domain.enums.VehicleType;
import fr.irvineflth.EcoFleet.dto.VehicleDto;
import fr.irvineflth.EcoFleet.exception.ResourceNotFoundException;
import fr.irvineflth.EcoFleet.mapper.VehicleMapper;
import fr.irvineflth.EcoFleet.repository.VehicleRepository;
import fr.irvineflth.EcoFleet.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceImplTest {

    @Mock
    private VehicleRepository repository;

    @Mock
    private VehicleMapper mapper;

    @InjectMocks
    private VehicleServiceImpl service;

    @Test
    void shouldReturnVehicleById() {
        Vehicle vehicle = new Vehicle(1L, "EV-123", VehicleType.CAR, "Renault", "Zoé", 250, 10000, LocalDate.now(), true);
        VehicleDto dto = new VehicleDto(1L, "EV-123", VehicleType.CAR, "Renault", "Zoé", 250, 10000, LocalDate.now(), true);

        when(this.repository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(this.mapper.toDto(vehicle)).thenReturn(dto);

        VehicleDto result = this.service.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowExceptionIfVehicleNotFound() {
        when(this.repository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> this.service.findById(42L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Vehicle not found with id: 42");
    }

    @Test
    void shouldReturnAllVehicles() {
        Vehicle v1 = new Vehicle(1L, "EV-1", VehicleType.BIKE, "Decathlon", "Elops", 80, 500, LocalDate.now(), true);
        Vehicle v2 = new Vehicle(2L, "EV-2", VehicleType.VAN, "Xiaomi", "M365", 30, 200, LocalDate.now(), true);

        VehicleDto dto1 = new VehicleDto(1L, "EV-1", VehicleType.BIKE, "Decathlon", "Elops", 80, 500, v1.getLastMaintenanceDate(), true);
        VehicleDto dto2 = new VehicleDto(2L, "EV-2", VehicleType.VAN, "Xiaomi", "M365", 30, 200, v2.getLastMaintenanceDate(), true);

        when(this.repository.findAll()).thenReturn(List.of(v1, v2));
        when(this.mapper.toDtoList(Arrays.asList(v1, v2))).thenReturn(Arrays.asList(dto1, dto2));

        List<VehicleDto> result = this.service.findAll();

        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactly(dto1, dto2);
    }
}
