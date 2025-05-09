package fr.irvineflth.EcoFleet.unit.service;

import fr.irvineflth.EcoFleet.domain.entity.Maintenance;
import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.dto.MaintenanceDto;
import fr.irvineflth.EcoFleet.exception.ResourceNotFoundException;
import fr.irvineflth.EcoFleet.mapper.MaintenanceMapper;
import fr.irvineflth.EcoFleet.repository.MaintenanceRepository;
import fr.irvineflth.EcoFleet.repository.VehicleRepository;
import fr.irvineflth.EcoFleet.service.impl.MaintenanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MaintenanceServiceImplTest {

    @Mock
    private MaintenanceRepository maintenanceRepo;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private MaintenanceMapper mapper;

    @InjectMocks
    private MaintenanceServiceImpl service;

    private final Vehicle vehicle = Vehicle.builder().id(1L).model("Renault Zoe").build();

    @Test
    void shouldReturnAllMaintenances() {
        Maintenance m = new Maintenance(1L, "Vidange", LocalDate.now(), 100, vehicle);
        MaintenanceDto dto = MaintenanceDto.builder().id(1L).type("Vidange").date(LocalDate.now()).cost(100).vehicleId(1L).build();

        when(this.maintenanceRepo.findAll()).thenReturn(List.of(m));
        when(this.mapper.toDto(m)).thenReturn(dto);

        List<MaintenanceDto> result = this.service.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo("Vidange");
    }

    @Test
    void shouldThrowIfVehicleNotFoundOnCreate() {
        MaintenanceDto dto = MaintenanceDto.builder().type("Révision").vehicleId(99L).build();

        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> this.service.create(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Vehicle not found");
    }
}
