import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProfessioanlRequest, ProfessioanlResponse } from './professional-model';
import { environment } from '../../../../environments/environment';
import { Page } from '../appointments/appointment-model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfessionalService {

  private baseUrl = `${environment.apiUrl}/professionals`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<ProfessioanlResponse[]> {
    return this.http.get<ProfessioanlResponse[]>(`${this.baseUrl}/all`);
  }

  getAllPaged(page?: number, size?: number): Observable<Page<ProfessioanlResponse>> {
    return this.http.get<Page<ProfessioanlResponse>>(
      `${this.baseUrl}?page=${page}&size=${size}`
    )
  }

  getById(id: string) {
    return this.http.get<ProfessioanlResponse>(`${this.baseUrl}/${id}`);
  }

  getByEmail(email: string) {
    return this.http.get<ProfessioanlResponse>(`${this.baseUrl}/email/${email}`);
  }

  create(data: ProfessioanlRequest) {
    return this.http.post(this.baseUrl, data);
  }

  update(id: string, data: ProfessioanlRequest) {
    return this.http.put(`${this.baseUrl}/${id}`, data);
  }

  delete(id: string) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

}