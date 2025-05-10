import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Vehicle} from '../core/models/vehicle.model';
import {VehicleService} from '../core/services/vehicle.service';
import {VehicleType} from '../core/models/vehicle-type.enum';
import {VehicleDto} from '../core/dto/vehicle.dto';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {faPen, faTrash} from '@fortawesome/free-solid-svg-icons';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-vehicle-list',
  imports: [CommonModule, ReactiveFormsModule, FaIconComponent],
  templateUrl: './vehicle-list.component.html',
  styleUrl: './vehicle-list.component.scss'
})
export class VehicleListComponent implements OnInit {
  protected readonly faTrash = faTrash;
  protected readonly faPen = faPen;

  vehicles: Vehicle[] = [];
  isModalOpen: boolean = false;
  vehiclesTypes: VehicleType[] = [VehicleType.CAR, VehicleType.BIKE, VehicleType.VAN];

  vehicleForm!: FormGroup;

  currentEditingId: number | null = null;

  constructor(
    private vehicleService: VehicleService,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.fetchVehicles();
    this.initForm();
  }

  initForm(): void {
    this.vehicleForm = this.fb.group({
      registrationNumber: ['', Validators.required],
      brand: ['', Validators.required],
      model: ['', Validators.required],
      type: [VehicleType.CAR, Validators.required],
      autonomyKm: ['', [Validators.required, Validators.min(1)]],
      currentKm: ['', [Validators.required, Validators.min(1)]],
      available: [true, Validators.required],
    });
  }

  fetchVehicles(): void {
    this.vehicleService.getAll().subscribe({
      next: (data) => {
        this.vehicles = data;
        console.log(data);
      },
      error: (err) => console.log('Failed to load vehicles', err),
    });
  }

  openModal(): void {
    this.isModalOpen = true;
    this.vehicleForm.reset({
      registrationNumber: '',
      brand: '',
      model: '',
      type: VehicleType.CAR,
      autonomyKm: 0,
      currentKm: 0,
      available: false,
    });
  }

  closeModal(): void {
    this.isModalOpen = false;
  }

  onSubmit(): void {
    if (this.vehicleForm.invalid) return;

    const formValue: VehicleDto = this.vehicleForm.value;

    if (this.currentEditingId) {
      this.vehicleService.update(this.currentEditingId, formValue).subscribe({
        next: () => {
          this.fetchVehicles();
          this.toastr.success('Vehicle updated successfully.');
          this.closeModal();
          this.vehicleForm.reset();
        },
        error: (err) => {
          console.log('Failed to update vehicle', err);
          this.toastr.error('Failed to update vehicle.', 'Error');
        }
      })
    } else {
      this.vehicleService.create(formValue).subscribe({
        next: () => {
          this.fetchVehicles();
          this.closeModal();
          this.vehicleForm.reset();
          this.toastr.success('Vehicle created successfully.');
        },
        error: err => {
          console.log('Failed to create vehicle', err);
          this.toastr.error('Failed to create vehicle.', 'Error');
        }
      })
    }
  }

  editVehicle(vehicle: Vehicle): void {
    this.isModalOpen = true;
    this.currentEditingId = vehicle.id;

    this.vehicleForm.patchValue({
      'registrationNumber': vehicle.registrationNumber,
      'brand': vehicle.brand,
      'model': vehicle.model,
      'type': vehicle.type,
      'autonomyKm': vehicle.autonomyKm,
      'currentKm': vehicle.currentKm,
      'available': vehicle.available ?? false,
    });
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

  get registrationNumber(): FormControl<string> {
    return this.vehicleForm.get('registrationNumber') as FormControl<string>;
  }

  get brand(): FormControl<string> {
    return this.vehicleForm.get('brand') as FormControl<string>;
  }

  get model(): FormControl<string> {
    return this.vehicleForm.get('model') as FormControl<string>;
  }

  get type(): FormControl<VehicleType> {
    return this.vehicleForm.get('type') as FormControl<VehicleType>;
  }

  get autonomyKm(): FormControl<number> {
    return this.vehicleForm.get('autonomyKm') as FormControl<number>;
  }

  get currentKm(): FormControl<number> {
    return this.vehicleForm.get('currentKm') as FormControl<number>;
  }

  get available(): FormControl<boolean> {
    return this.vehicleForm.get('available') as FormControl<boolean>;
  }
}
