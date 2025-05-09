package fr.irvineflth.EcoFleet.integration.repository;

import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.domain.enums.VehicleType;
import fr.irvineflth.EcoFleet.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository repository;

    @Test
    void shouldPersistAndRetrieveVehicle() {
        Vehicle vehicle = new Vehicle(null, "TEST-001", VehicleType.CAR, "Peugeot", "e-208", 300, 12000, LocalDate.now(), true);
        Vehicle saved = this.repository.save(vehicle);

        assertThat(saved.getId()).isNotNull();

        Vehicle found = this.repository.findById(saved.getId()).orElseThrow();
        assertThat(found.getRegistrationNumber()).isEqualTo("TEST-001");
    }
}
