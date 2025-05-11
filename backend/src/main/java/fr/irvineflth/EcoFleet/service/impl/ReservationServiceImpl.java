package fr.irvineflth.EcoFleet.service.impl;

import fr.irvineflth.EcoFleet.domain.entity.Reservation;
import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.dto.ReservationDto;
import fr.irvineflth.EcoFleet.exception.ResourceNotFoundException;
import fr.irvineflth.EcoFleet.mapper.ReservationMapper;
import fr.irvineflth.EcoFleet.repository.ReservationRepository;
import fr.irvineflth.EcoFleet.repository.VehicleRepository;
import fr.irvineflth.EcoFleet.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository repository;
    private final VehicleRepository vehicleRepository;
    private final ReservationMapper mapper;

    @Override
    public List<ReservationDto> findAll() {
        return this.mapper.toDtoList(this.repository.findAll());
    }

    @Override
    public ReservationDto findById(Long id) {
        Reservation reservation = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        return this.mapper.toDto(reservation);
    }

    @Override
    public ReservationDto create(ReservationDto dto) {
        Vehicle vehicle = this.vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + dto.getVehicleId()));
        Reservation reservation = this.mapper.toEntity(dto, vehicle);
        return this.mapper.toDto(this.repository.save(reservation));
    }

    @Override
    public void delete(Long id) {
        if (!this.repository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation not found with id: " + id);
        }
        this.repository.deleteById(id);
    }
}
