import {VehicleType} from '../models/vehicle-type.enum';

export interface CreateVehicleDto {
  registrationNumber: string;
  type: VehicleType;
  brand: string;
  model: string;
  autonomyKm: number;
  currentKm: number;
}
