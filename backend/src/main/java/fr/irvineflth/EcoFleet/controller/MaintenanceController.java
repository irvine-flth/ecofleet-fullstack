package fr.irvineflth.EcoFleet.controller;

import fr.irvineflth.EcoFleet.dto.MaintenanceDto;
import fr.irvineflth.EcoFleet.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {
    private final MaintenanceService service;

    @GetMapping
    public ResponseEntity<List<MaintenanceDto>> findAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findById(id));
    }

    @PostMapping
    public ResponseEntity<MaintenanceDto> create(@RequestBody MaintenanceDto dto) {
        return ResponseEntity.ok(this.service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceDto> update(@PathVariable Long id, @RequestBody MaintenanceDto dto) {
        return ResponseEntity.ok(this.service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<MaintenanceDto>> findByVehicleId(@PathVariable Long vehicleId) {
        return ResponseEntity.ok(this.service.findByVehicleId(vehicleId));
    }
}
