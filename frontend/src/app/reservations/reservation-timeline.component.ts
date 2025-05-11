import {Component, OnInit} from '@angular/core';
import {Vehicle} from '../core/models/vehicle.model';
import {Reservation} from '../core/models/reservation';
import { VehicleService } from '../core/services/vehicle.service';
import {ReservationService} from '../core/services/reservation.service';
import {DatePipe, NgForOf} from '@angular/common';

@Component({
  selector: 'app-reservation-timeline',
  imports: [
    NgForOf,
    DatePipe
  ],
  templateUrl: './reservation-timeline.component.html',
  styleUrl: './reservation-timeline.component.scss'
})
export class ReservationTimelineComponent implements OnInit {
  vehicles: Vehicle[] = [];
  reservations: Reservation[] = [];
  timelineDays: string[] = [];
  startOfRange: Date = new Date('2025-05-01');
  endOfRange: Date = new Date('2025-05-31');

  constructor(
    private vehicleService: VehicleService,
    private reservationService: ReservationService
  ) { }

  ngOnInit(): void {
    this.generateTimelineDays();
    this.vehicleService.getAll().subscribe(v => this.vehicles = v);
    this.reservationService.getAll().subscribe(r => this.reservations = r);
  }

  private generateTimelineDays(): void {
    const day = new Date(this.startOfRange);
    while (day <= this.endOfRange) {
      this.timelineDays.push(day.toISOString().split('T')[0]);
      day.setDate(day.getDate() + 1);
    }
  }

  getReservationStyle(res: Reservation): { left: string; width: string } {
    const totalDays = this.timelineDays.length;
    const startIndex = this.timelineDays.indexOf(res.startDate);
    const endIndex = this.timelineDays.indexOf(res.endDate);
    const left = (startIndex / totalDays) * 100;
    const width = ((endIndex - startIndex + 1) / totalDays) * 100;

    return {
      left: `${left}%`,
      width: `${width}%`,
    };
  }
}
