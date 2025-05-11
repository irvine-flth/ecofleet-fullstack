package fr.irvineflth.EcoFleet.repository;

import fr.irvineflth.EcoFleet.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByVehicleId(Long vehicleId);
}
