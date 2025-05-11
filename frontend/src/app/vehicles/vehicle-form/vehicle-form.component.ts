import {Component, EventEmitter, Input, Output, SimpleChanges} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Vehicle} from '../../core/models/vehicle.model';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {VehicleType} from '../../core/models/vehicle-type.enum';

@Component({
  selector: 'app-vehicle-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './vehicle-form.component.html',
  styleUrl: './vehicle-form.component.scss'
})
export class VehicleFormComponent {
  @Input() vehicle: Vehicle | null = null;
  @Output() submitForm = new EventEmitter<Vehicle>();
  @Output() cancel = new EventEmitter<void>();

  vehicleForm!: FormGroup;
  vehicleTypes = Object.values(VehicleType);

  constructor(
    private fb: FormBuilder
  ) {
    this.initForm();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['vehicle']) {
      this.initForm();
    }
  }

  private initForm(): void {
    this.vehicleForm = this.fb.group({
      registrationNumber: [this.vehicle?.registrationNumber || '', Validators.required],
      brand: [this.vehicle?.brand || '', Validators.required],
      model: [this.vehicle?.model || '', Validators.required],
      type: [this.vehicle?.type || VehicleType.CAR, Validators.required],
      autonomyKm: [this.vehicle?.autonomyKm || 0, [Validators.required, Validators.min(1)]],
      currentKm: [this.vehicle?.currentKm || 0, [Validators.required, Validators.min(0)]],
      available: [this.vehicle?.available ?? false],
    });
    this.vehicleForm.markAllAsTouched();
  }

  onSubmit(): void {
    if (this.vehicleForm.invalid) return;
    this.submitForm.emit(this.vehicleForm.value);
  }

  onCancel(): void {
    this.cancel.emit();
  }

  get registrationNumber() {
    return this.vehicleForm.get('registrationNumber')!;
  }
  get type() {
    return this.vehicleForm.get('type')!;
  }
  get brand() {
    return this.vehicleForm.get('brand')!;
  }
  get model() {
    return this.vehicleForm.get('model')!;
  }
  get autonomyKm() {
    return this.vehicleForm.get('autonomyKm')!;
  }
  get currentKm() {
    return this.vehicleForm.get('currentKm')!;
  }
  get available() {
    return this.vehicleForm.get('available')!;
  }

}
