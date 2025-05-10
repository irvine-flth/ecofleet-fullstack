import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Maintenance} from '../models/maintenance.model';

@Injectable({
  providedIn: 'root'
})
export class MaintenanceService {
  private baseUrl: string = 'http://localhost:8080/api/maintenances';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Maintenance[]> {
    return this.http.get<Maintenance[]>(this.baseUrl);
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
