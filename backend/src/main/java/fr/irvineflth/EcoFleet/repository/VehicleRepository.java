package fr.irvineflth.EcoFleet.repository;

import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByRegistrationNumber(String registrationNumber);
}
