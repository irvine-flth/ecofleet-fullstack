import {VehicleType} from './vehicle-type.enum';

export interface Vehicle {
  id: number;
  registrationNumber: string;
  type: VehicleType;
  brand: string;
  model: string;
  autonomyKm: number;
  currentKm: number;
  lastMaintenanceDate: string;
  available: string;
}
