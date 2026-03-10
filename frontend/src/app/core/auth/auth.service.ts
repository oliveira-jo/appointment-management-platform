import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse } from './auth.model';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private api = 'http://localhost:8080/api/v1/auth';

  constructor(private http: HttpClient) { }

  login(data: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.api}/login`, data)
      .pipe(
        tap(response => {
          localStorage.setItem('token', response.accessToken);
          localStorage.setItem('expiresIn', response.expiresIn.toString());
          localStorage.setItem('email', response.email);
        })
      );
  }

  saveToken(token: string) {
    localStorage.setItem("token", token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('expiresIn');
    localStorage.removeItem('email');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}