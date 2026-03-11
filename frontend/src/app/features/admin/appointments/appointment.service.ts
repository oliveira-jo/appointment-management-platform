import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppointmentRequest, AppointmentResponse, MetricsResponse, Page } from './appointment-model';

declare var bootstrap: any;

@Injectable({ providedIn: 'root' })
export class AppointmentService {

  private apiUrl = 'http://localhost:8080/api/v1/appointments';

  constructor(private http: HttpClient) { }

  getAll(page: number = 0, size: number = 20): Observable<Page<AppointmentResponse>> {
    return this.http.get<Page<AppointmentResponse>>(
      `${this.apiUrl}?page=${page}&size=${size}`
    )
  }

  getById(id: string) {
    return this.http.get<AppointmentResponse>(`${this.apiUrl}/${id}`);
  }

  getByDay(date: string) {
    return this.http.get<any[]>(`${this.apiUrl}/day/${date}`);
  }

  getMetrics(): MetricsResponse {
    const metrics: MetricsResponse = { today: 1, week: 1, revenueToday: 0, total: 2 };
    return metrics;
  }

  create(data: AppointmentRequest) {
    return this.http.post(this.apiUrl, data);
  }

  update(id: string, data: AppointmentRequest) {
    return this.http.put(`${this.apiUrl}/${id}`, data);
  }

  delete(id: string) {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

}




