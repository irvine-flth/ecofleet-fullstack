package fr.irvineflth.EcoFleet.service;

import fr.irvineflth.EcoFleet.domain.entity.Reservation;
import fr.irvineflth.EcoFleet.dto.ReservationDto;

import java.util.List;

public interface ReservationService {
    List<ReservationDto> findAll();
    ReservationDto findById(Long id);
    ReservationDto create(ReservationDto dto);
    void delete(Long id);
}
