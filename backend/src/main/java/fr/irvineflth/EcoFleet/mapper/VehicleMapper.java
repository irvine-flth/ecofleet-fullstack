package fr.irvineflth.EcoFleet.mapper;

import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.dto.VehicleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleDto toDto(Vehicle entity);
    Vehicle toEntity(VehicleDto dto);
    List<VehicleDto> toDtoList(List<Vehicle> entities);

    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class); // For unit tests
}
