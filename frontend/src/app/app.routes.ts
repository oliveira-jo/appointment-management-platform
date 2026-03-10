import { Routes } from '@angular/router';
import { AppointmentListComponent } from './features/appointments/pages/appointment-list/appointment-list.component';
import { LoginComponent } from './features/login/login.component';
import { authGuard } from './core/auth/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'appointments', component: AppointmentListComponent, canActivate: [authGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];