import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProductRequest, ProductResponse } from './product-modal';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl = `${environment.apiUrl}/products`;

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get(this.baseUrl);
  }

  getById(id: string) {
    return this.http.get<ProductResponse>(`${this.baseUrl}/${id}`);
  }

  getByName(name: string) {
    return this.http.get<ProductResponse[]>(`${this.baseUrl}/name/${name}`);
  }

  create(data: ProductRequest) {
    return this.http.post(this.baseUrl, data);
  }

  update(id: string, data: ProductRequest) {
    return this.http.put(`${this.baseUrl}/${id}`, data);
  }

  delete(id: string) {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

}