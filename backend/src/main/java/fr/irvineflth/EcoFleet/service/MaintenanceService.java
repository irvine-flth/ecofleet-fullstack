package fr.irvineflth.EcoFleet.service;

import fr.irvineflth.EcoFleet.dto.MaintenanceDto;

import java.util.List;

public interface MaintenanceService {
    List<MaintenanceDto> findAll();
    MaintenanceDto findById(Long id);
    MaintenanceDto create(MaintenanceDto dto);
    MaintenanceDto update(Long id, MaintenanceDto dto);
    void delete(Long id);
    List<MaintenanceDto> findByVehicleId(Long vehicleId);
}
