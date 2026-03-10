import { Routes } from '@angular/router';
import { AppointmentListComponent } from './features/admin/appointments/pages/appointment-list/appointment-list.component';
import { LoginComponent } from './features/login/login.component';
import { authGuard } from './core/auth/auth.guard';
import { DashboardComponent } from './features/admin/dashboard/dashboard.component';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { ProfessionalListComponent } from './features/admin/professionals/pages/professional-list/professional-list.component';
import { CustomerListComponent } from './features/admin/customers/pages/customer-list/customer-list.component';
import { ProductListComponent } from './features/admin/products/pages/product-list/product-list.component';

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
      { path: 'appointments', component: AppointmentListComponent },
      { path: 'professionals', component: ProfessionalListComponent },
      { path: 'customers', component: CustomerListComponent },
      { path: 'products', component: ProductListComponent },
    ]
  },

  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }

];