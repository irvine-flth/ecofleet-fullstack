package fr.irvineflth.EcoFleet.service.impl;

import fr.irvineflth.EcoFleet.domain.entity.Maintenance;
import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.dto.MaintenanceDto;
import fr.irvineflth.EcoFleet.exception.ResourceNotFoundException;
import fr.irvineflth.EcoFleet.mapper.MaintenanceMapper;
import fr.irvineflth.EcoFleet.repository.MaintenanceRepository;
import fr.irvineflth.EcoFleet.repository.VehicleRepository;
import fr.irvineflth.EcoFleet.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;
    private final MaintenanceMapper mapper;

    @Override
    public List<MaintenanceDto> findAll() {
        return this.maintenanceRepository.findAll()
                .stream()
                .map(this.mapper::toDto)
                .toList();
    }

    @Override
    public MaintenanceDto findById(Long id) {
        Maintenance maintenance = this.maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance not found with id: " + id));
        return this.mapper.toDto(maintenance);
    }

    @Override
    public MaintenanceDto create(MaintenanceDto dto) {
        Vehicle vehicle = this.vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + dto.getVehicleId()));

        Maintenance maintenance = this.mapper.toEntity(dto, vehicle);

        Maintenance saved = this.maintenanceRepository.save(maintenance);

        if (vehicle.getLastMaintenanceDate() == null || dto.getDate().isAfter(vehicle.getLastMaintenanceDate())) {
            vehicle.setLastMaintenanceDate(dto.getDate());
            this.vehicleRepository.save(vehicle);
        }

        return this.mapper.toDto(saved);
    }

    @Override
    public MaintenanceDto update(Long id, MaintenanceDto dto) {
        Maintenance existing = this.maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance not found with id: " + id));

        Vehicle vehicle = this.vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + dto.getVehicleId()));

        existing.setType(dto.getType());
        existing.setDate(dto.getDate());
        existing.setCost(dto.getCost());
        existing.setVehicle(vehicle);

        return this.mapper.toDto(maintenanceRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        this.maintenanceRepository.deleteById(id);
    }

    @Override
    public List<MaintenanceDto> findByVehicleId(Long vehicleId) {
        return this.maintenanceRepository.findByVehicleId(vehicleId)
                .stream()
                .map(this.mapper::toDto)
                .toList();
    }
}
