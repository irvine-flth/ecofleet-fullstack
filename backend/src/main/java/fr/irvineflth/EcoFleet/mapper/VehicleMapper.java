package fr.irvineflth.EcoFleet.mapper;

import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.dto.VehicleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleDto toDto(Vehicle entity);
    Vehicle toEntity(VehicleDto dto);
    List<VehicleDto> toDtoList(List<Vehicle> entities);
}
