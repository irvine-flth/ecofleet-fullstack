import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Vehicle} from '../core/models/vehicle.model';
import {VehicleService} from '../core/services/vehicle.service';
import {VehicleType} from '../core/models/vehicle-type.enum';
import {CreateVehicleDto} from '../core/dto/create-vehicle.dto';
import {FormsModule, NgForm} from '@angular/forms';

@Component({
  selector: 'app-vehicle-list',
  imports: [CommonModule, FormsModule],
  templateUrl: './vehicle-list.component.html',
  styleUrl: './vehicle-list.component.scss'
})
export class VehicleListComponent implements OnInit{
  vehicles: Vehicle[] = [];
  isModalOpen: boolean = false;
  vehiclesTypes: VehicleType[] = [VehicleType.CAR, VehicleType.BIKE, VehicleType.VAN];

  newVehicle: CreateVehicleDto = {
    registrationNumber: '',
    brand: '',
    model: '',
    type: VehicleType.CAR,
    autonomyKm: 0,
    currentKm: 0,
  };

  constructor(private vehicleService: VehicleService) {}

  ngOnInit(): void{
    this.fetchVehicles();
  }

  fetchVehicles(): void {
    this.vehicleService.getAll().subscribe({
      next: (data) => this.vehicles = data,
      error: (err) => console.log('Failed to load vehicles', err),
    });
  }

  openModal(): void {
    this.isModalOpen = true;
  }

  closeModal(): void {
    this.isModalOpen = false;
  }

  onSubmit(form: NgForm): void {
    if (form.valid) {
      this.vehicleService.create(this.newVehicle).subscribe({
        next: () => {
          this.fetchVehicles();
          this.closeModal();
          form.resetForm();
        },
        error: err => console.log('Failed to create vehicle', err)
      })
    }
  }
}
