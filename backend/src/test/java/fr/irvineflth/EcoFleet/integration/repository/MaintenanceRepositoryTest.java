package fr.irvineflth.EcoFleet.integration.repository;

import fr.irvineflth.EcoFleet.domain.entity.Maintenance;
import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.repository.MaintenanceRepository;
import fr.irvineflth.EcoFleet.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MaintenanceRepositoryTest {

    @Autowired
    private MaintenanceRepository repository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    void shouldPersistAndRetrieveByVehicleId() {
        Vehicle vehicle = Vehicle.builder()
                .model("Fiat 500e")
                .brand("Fiat")
                .registrationNumber("EV-2024-FR")
                .build();

        vehicle = this.vehicleRepository.save(vehicle);

        Maintenance maintenance = Maintenance.builder()
                .type("Contrôle technique")
                .date(LocalDate.now())
                .cost(90)
                .vehicle(vehicle)
                .build();

        this.repository.save(maintenance);

        List<Maintenance> result = this.repository.findByVehicleId(vehicle.getId());

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo("Contrôle technique");
        assertThat(result.get(0).getVehicle().getId()).isEqualTo(vehicle.getId());
    }
}
