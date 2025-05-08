package fr.irvineflth.EcoFleet.controller;

import fr.irvineflth.EcoFleet.dto.VehicleDto;
import fr.irvineflth.EcoFleet.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService service;

    @GetMapping
    public ResponseEntity<List<VehicleDto>> getAll() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findById(id));
    }

    @PostMapping
    public ResponseEntity<VehicleDto> create(@RequestBody VehicleDto dto) {
        VehicleDto created = this.service.create(dto);
        return ResponseEntity.created(URI.create("/api/vehicles/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDto> update(@PathVariable Long id, @RequestBody VehicleDto dto) {
        return ResponseEntity.ok(this.service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
