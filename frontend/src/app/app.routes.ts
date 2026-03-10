import { Routes } from '@angular/router';
import { AppointmentListComponent } from './features/appointments/pages/appointment-list/appointment-list.component';
import { LoginComponent } from './features/login/login.component';
import { authGuard } from './core/auth/auth.guard';
import { DashboardComponent } from './features/admin/dashboard/dashboard.component';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { ProfessionalListComponent } from './features/admin/professionals/pages/professional-list/professional-list.component';

export const routes: Routes = [

  // rota pública
  { path: 'login', component: LoginComponent },

  // rotas protegidas
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [

      { path: 'dashboard', component: DashboardComponent },

      { path: 'professionals', component: ProfessionalListComponent }

      // futuras rotas
      // { path: 'customers', component: CustomerListComponent },
      // { path: 'professionals', component: ProfessionalListComponent },
      // { path: 'products', component: ProductListComponent }

    ]
  },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }

];