import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Vehicle} from '../core/models/vehicle.model';
import {VehicleService} from '../core/services/vehicle.service';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {faPen, faTrash} from '@fortawesome/free-solid-svg-icons';
import {ToastrService} from 'ngx-toastr';
import {VehicleFormComponent} from './vehicle-form/vehicle-form.component';

@Component({
  selector: 'app-vehicle-list',
  imports: [CommonModule, FaIconComponent, VehicleFormComponent],
  templateUrl: './vehicle-list.component.html',
  styleUrl: './vehicle-list.component.scss'
})
export class VehicleListComponent implements OnInit {
  protected readonly faTrash = faTrash;
  protected readonly faPen = faPen;

  vehicles: Vehicle[] = [];
  isModalOpen: boolean = false;
  selectedVehicle: Vehicle | null = null;

  constructor(
    private vehicleService: VehicleService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.fetchVehicles();
  }

  fetchVehicles(): void {
    this.vehicleService.getAll().subscribe({
      next: (data) => this.vehicles = data,
      error: (err) => console.log('Failed to load vehicles', err),
    });
  }

  openCreate(): void {
    this.selectedVehicle = null;
    this.isModalOpen = true;
  }

  closeModal(): void {
    this.isModalOpen = false;
    this.selectedVehicle = null;
  }

  editVehicle(vehicle: Vehicle): void {
    this.selectedVehicle = vehicle;
    this.isModalOpen = true;
  }

  deleteVehicle(id: number): void {
    if (confirm('Are you sure you want to delete this vehicle?')) {
      this.vehicleService.delete(id).subscribe({
        next: () => {
          this.fetchVehicles();
          this.toastr.success('Vehicle deleted successfully.');
        },
        error: err => {
          console.log('Failed to delete vehicle', err);
          this.toastr.error('Failed to delete vehicle.', 'Error');
        }
      });
    }
  }

  handleSubmit(formData: Omit<Vehicle, 'id'>): void {
    if (this.selectedVehicle) {
      this.vehicleService.update(this.selectedVehicle.id, formData).subscribe({
        next: () => {
          this.toastr.success('Vehicle updated successfully.');
          this.fetchVehicles();
          this.closeModal();
        },
        error: (err) => {
          if (err.status === 409) {
            this.toastr.error('Vehicle with this registration number already exists.', 'Error');
          } else {
            this.toastr.error('Failed to update vehicle.', 'Error')
          }
        }
      });
    } else {
      this.vehicleService.create(formData).subscribe({
        next: () => {
          this.toastr.success('Vehicle created successfully.');
          this.fetchVehicles();
          this.closeModal();
        },
        error: err => {
          if (err.status === 409) {
            this.toastr.error('Vehicle with this registration number already exists.', 'Error');
          } else {
            this.toastr.error('Failed to create vehicle : ' + err.error, 'Error')
          }
        }
      });
    }
  }
}
