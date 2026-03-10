import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProfessioanlRequest, ProfessioanlResponse } from './professional-model';

@Injectable({
  providedIn: 'root'
})
export class ProfessionalService {

  private API = "http://localhost:8080/api/v1/professionals";

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(this.API);
  }

  getById(id: string) {
    return this.http.get<ProfessioanlResponse>(`${this.API}/${id}`);
  }

  getByEmail(email: string) {
    return this.http.get<ProfessioanlResponse>(`${this.API}/email/${email}`);
  }

  create(data: ProfessioanlRequest) {
    return this.http.post(this.API, data);
  }

  update(id: string, data: ProfessioanlRequest) {
    return this.http.put(`${this.API}/${id}`, data);
  }

  delete(id: string) {
    return this.http.delete(`${this.API}/${id}`);
  }

}