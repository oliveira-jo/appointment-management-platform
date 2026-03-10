import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Appointment } from '../models/Appointment';
import { Page } from '../models/Page';

@Injectable({ providedIn: 'root' })
export class AppointmentService {

  private apiUrl = 'http://localhost:8080/api/v1/appointments';

  constructor(private http: HttpClient) { }

  getAll(page: number = 0, size: number = 20): Observable<Page<Appointment>> {
    return this.http.get<Page<Appointment>>(
      `${this.apiUrl}?page=${page}&size=${size}`
    )
  }
}