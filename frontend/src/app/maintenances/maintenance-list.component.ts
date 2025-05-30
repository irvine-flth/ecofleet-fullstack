import {Component, OnInit} from '@angular/core';
import {MaintenanceService} from '../core/services/maintenance.service';
import {Maintenance} from '../core/models/maintenance.model';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {FaIconComponent} from '@fortawesome/angular-fontawesome';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {faTrash} from '@fortawesome/free-solid-svg-icons';
import {ToastrService} from 'ngx-toastr';
import {Vehicle} from '../core/models/vehicle.model';
import {VehicleService} from '../core/services/vehicle.service';
import {dateNotInFutureValidator} from '../core/validators/date-not-in-future.validator';

@Component({
  selector: 'app-maintenance-list',
  imports: [
    FaIconComponent,
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './maintenance-list.component.html',
  styleUrl: './maintenance-list.component.scss'
})
export class MaintenanceListComponent implements OnInit{
  protected maintenances: Maintenance[] = [];
  protected vehicles: Vehicle[] = [];

  protected isModalOpen = false;

  protected maintenanceForm!: FormGroup;

  constructor(
    private service: MaintenanceService,
    private vehicleService: VehicleService,
    private toastr: ToastrService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.fetchMaintenances();
    this.fetchVehicles();
    this.initForm();
  }

  initForm(): void {
    this.maintenanceForm = this.fb.group({
      type: ['', Validators.required],
      date: ['', [Validators.required, dateNotInFutureValidator()]],
      cost: ['', [Validators.required, Validators.min(0)]],
      vehicleId: [null, Validators.required],
    });
    this.maintenanceForm.markAllAsTouched();
  }

  onSubmit(): void {
    if (this.maintenanceForm.invalid) return;

    const formValue = this.maintenanceForm.value;

    this.service.create({
      ...formValue,
      vehicle: { id: formValue.vehicle },
    }).subscribe({
      next: () => {
        this.fetchMaintenances();
        this.toastr.success('Maintenance created successfully');
        this.closeModal();
      },
      error: (error) => {
        console.error('Error creating maintenance', error);
        this.toastr.error('Failed to create maintenance');
      }
    })
  }

  fetchMaintenances(): void {
    this.service.getAll().subscribe({
      next: (data) => this.maintenances = data,
      error: (error) => console.error('Error fetching maintenances', error),
    });
  }

  fetchVehicles(): void {
    this.vehicleService.getAll().subscribe({
      next: (data) => this.vehicles = data,
      error: (error) => console.error('Error fetching vehicles', error),
    });
  }

  deleteMaintenance(id: number): void {
    if (confirm('Are you sure you want to delete this maintenance?')) {
      this.service.delete(id).subscribe({
        next: () => {
          this.fetchMaintenances();
          this.toastr.success('Maintenance deleted successfully');
        },
        error: (error) => {
          console.error('Error deleting maintenance', error);
          this.toastr.error('Failed to delete maintenance');
        }
      });
    }
  }

  openModal(): void {
    this.isModalOpen = true;
  }

  closeModal(): void {
    this.isModalOpen = false;
  }

  protected readonly faTrash = faTrash;

  get type() {
    return this.maintenanceForm.get('type')!;
  }

  get date() {
    return this.maintenanceForm.get('date')!;
  }

  get cost() {
    return this.maintenanceForm.get('cost')!;
  }

  get vehicleId() {
    return this.maintenanceForm.get('vehicleId')!;
  }
}
