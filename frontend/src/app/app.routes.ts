import { Routes } from '@angular/router';
import {VehicleListComponent} from './vehicles/vehicle-list.component';
import {MaintenanceListComponent} from './maintenances/maintenance-list.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'vehicles',
    pathMatch: 'full'
  },
  {
    path: 'vehicles',
    component: VehicleListComponent
  },
  {
    path: 'maintenances',
    component: MaintenanceListComponent
  }
];
