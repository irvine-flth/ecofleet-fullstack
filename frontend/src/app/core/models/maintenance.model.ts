import {Vehicle} from './vehicle.model';

export interface Maintenance {
  id: number;
  type: string;
  date: string;
  cost: number;
  vehicle: Vehicle;
}

export interface MaintenanceDto {
  id: number;
  type: string;
  date: string;
  cost: number;
  vehicleId: number;
}
