package fr.irvineflth.EcoFleet.service.impl;

import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.dto.VehicleDto;
import fr.irvineflth.EcoFleet.exception.RegistrationAlreadyExistsException;
import fr.irvineflth.EcoFleet.exception.ResourceNotFoundException;
import fr.irvineflth.EcoFleet.mapper.VehicleMapper;
import fr.irvineflth.EcoFleet.repository.VehicleRepository;
import fr.irvineflth.EcoFleet.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    @Override
    public List<VehicleDto> findAll() {
        return this.mapper.toDtoList(this.repository.findAll());
    }

    @Override
    public VehicleDto findById(long id) {
        return this.repository
                .findById(id)
                .map(this.mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    @Override
    public VehicleDto create(VehicleDto vehicleDto) {
        if (this.repository.existsByRegistrationNumber(vehicleDto.getRegistrationNumber())) {
            throw new RegistrationAlreadyExistsException("Registration number already exists");
        }
        Vehicle vehicle = this.mapper.toEntity(vehicleDto);
        return this.mapper.toDto(this.repository.save(vehicle));
    }

    @Override
    public VehicleDto update(long id, VehicleDto vehicleDto) {
        Vehicle existing = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        if (this.repository.existsByRegistrationNumber(vehicleDto.getRegistrationNumber())) {
            throw new RegistrationAlreadyExistsException("Registration number already exists");
        }

        Vehicle updated = this.mapper.toEntity(vehicleDto);
        updated.setId(existing.getId());
        return this.mapper.toDto(this.repository.save(updated));
    }

    @Override
    public void delete(long id) {
        this.repository.deleteById(id);
    }
}
