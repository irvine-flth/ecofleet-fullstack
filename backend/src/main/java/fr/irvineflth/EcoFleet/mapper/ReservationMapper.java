package fr.irvineflth.EcoFleet.mapper;

import fr.irvineflth.EcoFleet.domain.entity.Reservation;
import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.dto.ReservationDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    @Mapping(source = "vehicle.id", target = "vehicleId")
    ReservationDto toDto(Reservation entity);

    @Mapping(source = "dto.id", target = "id")
    @Mapping(target = "vehicle", expression = "java(vehicle)")
    Reservation toEntity(ReservationDto dto, Vehicle vehicle);

    List<ReservationDto> toDtoList(List<Reservation> entities);

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class); // For unit tests
}
