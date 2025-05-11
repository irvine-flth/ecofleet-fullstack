import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {forkJoin, map, Observable} from 'rxjs';
import {Maintenance, MaintenanceDto} from '../models/maintenance.model';
import {VehicleService} from './vehicle.service';

@Injectable({
  providedIn: 'root'
})
export class MaintenanceService {
  private baseUrl: string = 'http://localhost:8080/api/maintenances';

  constructor(
    private http: HttpClient,
    private vehicleService: VehicleService,
  ) { }

  getAll(): Observable<Maintenance[]> {
    return forkJoin({
      maintenances: this.http.get<MaintenanceDto[]>(this.baseUrl),
      vehicles: this.vehicleService.getAll()
    }).pipe(
      map(({ maintenances, vehicles }) =>
        maintenances.map(dto => {
          const vehicle = vehicles.find(v => v.id === dto.vehicleId)!;
          return {
            id: dto.id,
            type: dto.type,
            date: dto.date,
            cost: dto.cost,
            vehicle: vehicle
          } satisfies Maintenance;
        })
      )
    );
  }

  getById(id: number): Observable<Maintenance> {
    return this.http.get<Maintenance>(`${this.baseUrl}/${id}`);
  }

  create(maintenance: Maintenance): Observable<Maintenance> {
    return this.http.post<Maintenance>(this.baseUrl, maintenance);
  }

  update(id: number, maintenance: Maintenance): Observable<Maintenance> {
    return this.http.put<Maintenance>(`${this.baseUrl}/${id}`, maintenance);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
