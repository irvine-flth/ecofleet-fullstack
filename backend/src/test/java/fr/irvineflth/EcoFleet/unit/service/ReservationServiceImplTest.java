package fr.irvineflth.EcoFleet.unit.service;

import fr.irvineflth.EcoFleet.domain.entity.Reservation;
import fr.irvineflth.EcoFleet.domain.entity.Vehicle;
import fr.irvineflth.EcoFleet.dto.ReservationDto;
import fr.irvineflth.EcoFleet.mapper.ReservationMapper;
import fr.irvineflth.EcoFleet.repository.ReservationRepository;
import fr.irvineflth.EcoFleet.repository.VehicleRepository;
import fr.irvineflth.EcoFleet.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationServiceImpl service;

    @Test
    void shouldReturnAllReservations() {
        Reservation res = new Reservation();

        when(reservationRepository.findAll()).thenReturn(List.of(res));
        when(reservationMapper.toDtoList(List.of(res))).thenReturn(List.of(new ReservationDto()));

        List<ReservationDto> result = this.service.findAll();

        assertThat(result).hasSize(1);
        verify(reservationRepository).findAll();
    }

    @Test
    void shouldCreateReservation() {
        ReservationDto dto = new ReservationDto(null, LocalDate.now(), LocalDate.now().plusDays(1), "John Doe", 1L);
        Vehicle vehicle = new Vehicle();
        Reservation reservation = new Reservation();

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(reservationMapper.toEntity(dto, vehicle)).thenReturn(reservation);
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        when(reservationMapper.toDto(reservation)).thenReturn(new ReservationDto(1L, dto.getStartDate(), dto.getEndDate(), dto.getDriverName(), 1L));

        ReservationDto result = this.service.create(dto);

        assertThat(result.getId()).isEqualTo(1L);
        verify(reservationRepository).save(reservation);
    }
}
