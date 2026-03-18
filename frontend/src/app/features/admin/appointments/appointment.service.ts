import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppointmentRequest, AppointmentResponse, Metrics, Page } from './appointment-model';
import { environment } from '../../../../environments/environment';

declare var bootstrap: any;

@Injectable({ providedIn: 'root' })
export class AppointmentService {

  private baseUrl = `${environment.apiUrl}/appointments`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<AppointmentResponse[]> {
    return this.http.get<AppointmentResponse[]>(`${this.baseUrl}/all`);
  }

  getAllPaged(page?: number, size?: number): Observable<Page<AppointmentResponse>> {
    return this.http.get<Page<AppointmentResponse>>(
      `${this.baseUrl}?page=${page}&size=${size}`
    )
  }

  getMetrics() {
    return this.http.get<Metrics>(`${this.baseUrl}/metrics`);
  }

  getByDay(date: string) {
    return this.http.get<any[]>(`${this.baseUrl}/day/${date}`);
  }

  create(data: AppointmentRequest) {
    return this.http.post(this.baseUrl, data);
  }

  delete(id: string) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

}




