import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatTableModule} from '@angular/material/table';
import {MatCardModule} from '@angular/material/card';
import {Vehicle} from '../core/models/vehicle.model';
import {VehicleService} from '../core/services/vehicle.service';

@Component({
  selector: 'app-vehicle-list',
  imports: [CommonModule, MatTableModule, MatCardModule],
  templateUrl: './vehicle-list.component.html',
  styleUrl: './vehicle-list.component.scss'
})
export class VehicleListComponent implements OnInit{
  vehicles: Vehicle[] = [];
  displayedColumns: string[] = [
    'registrationNumber', 'type', 'brand', 'model', 'autonomyKm', 'currentKm', 'lastMaintenanceDate', 'isAvailable'
  ];

  constructor(private vehicleService: VehicleService) {}

  ngOnInit(): void{
    this.vehicleService.getAll().subscribe({
      next: (data) => this.vehicles = data,
      error: (err) => console.log('Failed to load vehicles', err),
    });
  }
}
