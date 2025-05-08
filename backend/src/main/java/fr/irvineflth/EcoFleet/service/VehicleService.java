package fr.irvineflth.EcoFleet.service;

import fr.irvineflth.EcoFleet.dto.VehicleDto;

import java.util.List;

public interface VehicleService {
    List<VehicleDto> findAll();
    VehicleDto findById(long id);
    VehicleDto create(VehicleDto vehicleDto);
    VehicleDto update(long id, VehicleDto vehicleDto);
    void delete(long id);
}
