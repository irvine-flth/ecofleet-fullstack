package fr.irvineflth.EcoFleet.mapper;

import fr.irvineflth.EcoFleet.domain.entity.Maintenance;
import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.dto.MaintenanceDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    MaintenanceDto toDto(Maintenance maintenance);

    @Mapping(target = "vehicle", ignore = true)
    Maintenance toEntity(MaintenanceDto maintenanceDto, @Context Vehicle vehicle);

    MaintenanceMapper INSTANCE = Mappers.getMapper(MaintenanceMapper.class);
}
