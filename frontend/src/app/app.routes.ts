import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { AppointmentListComponent } from './features/appointments/pages/appointment-list/appointment-list.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'appointments', component: AppointmentListComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];