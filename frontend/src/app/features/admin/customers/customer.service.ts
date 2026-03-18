import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CustomerRequest, CustomerResponse } from './customer-modal';
import { environment } from '../../../../environments/environment';
import { Page } from '../appointments/appointment-model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private baseUrl = `${environment.apiUrl}/customers`;


  constructor(private http: HttpClient) { }

  getAll(): Observable<CustomerResponse[]> {
    return this.http.get<CustomerResponse[]>(`${this.baseUrl}/all`);
  }

  getAllPaged(page?: number, size?: number): Observable<Page<CustomerResponse>> {
    return this.http.get<Page<CustomerResponse>>(
      `${this.baseUrl}?page=${page}&size=${size}`
    )
  }

  getById(id: string) {
    return this.http.get<CustomerResponse>(`${this.baseUrl}/${id}`);
  }

  getByEmail(email: string) {
    return this.http.get<CustomerResponse>(`${this.baseUrl}/email/${email}`);
  }

  create(data: CustomerRequest) {
    return this.http.post(this.baseUrl, data);
  }

  update(id: string, data: CustomerRequest) {
    return this.http.put(`${this.baseUrl}/${id}`, data);
  }

  delete(id: string) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

}