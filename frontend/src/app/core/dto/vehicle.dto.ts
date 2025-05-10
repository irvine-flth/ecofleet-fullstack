import {VehicleType} from '../models/vehicle-type.enum';

export interface VehicleDto {
  registrationNumber: string;
  type: VehicleType;
  brand: string;
  model: string;
  autonomyKm: number;
  currentKm: number;
  available: boolean;
}
