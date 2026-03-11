import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppointmentService } from '../../appointment.service';
import { CustomerService } from '../../../customers/customer.service';
import { ProfessionalService } from '../../../professionals/professional.service';
import { CustomerResponse } from '../../../customers/customer-modal';
import { ProfessioanlResponse } from '../../../professionals/professional-model';
import { AppointmentRequest } from '../../appointment-model';
import { FormsModule } from '@angular/forms';
import { ProductResponse } from '../../../products/product-modal';
import { ProductService } from '../../../products/product.service';

@Component({
  selector: 'app-appointment-form',
  imports: [FormsModule],
  templateUrl: './appointment-form.component.html',
  styleUrl: './appointment-form.component.css'
})
export class AppointmentFormComponent implements OnInit {

  customers: CustomerResponse[] = [];
  professionals: ProfessioanlResponse[] = [];
  products: ProductResponse[] = [];

  date = '';
  time = '13:00';

  appointment: AppointmentRequest = { customerEmail: '', professionalEmail: '', productName: '', scheduledAt: '' };

  constructor(
    private appointmentService: AppointmentService,
    private customerService: CustomerService,
    private professionalService: ProfessionalService,
    private productService: ProductService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadCustomers();
    this.loadProfessionals();
    this.loadProducts();
  }

  loadCustomers() {
    this.customerService.getAll()
      .subscribe((data: any) => {
        const list = this.customers = data as CustomerResponse[];
        this.customers = list.filter(p => p.role !== 'ROLE_ADMIN');
      });
  }

  loadProfessionals() {
    this.professionalService.getAll()
      .subscribe((data: any) => {
        const list = this.professionals = data as ProfessioanlResponse[];
        this.professionals = list.filter(p => p.role !== 'ROLE_ADMIN');
      });
  }

  loadProducts() {
    this.productService.getAll()
      .subscribe((data: any) => {
        const list = this.products = data as ProductResponse[];
      });
  }

  save() {

    // Formatin date for api rules
    const [year, month, day] = this.date.split('-');
    const formattedDate = `${day}/${month}/${year}`;

    const dateTime = formattedDate + " " + this.time + "";

    this.appointment = {
      customerEmail: this.appointment.customerEmail,
      professionalEmail: this.appointment.professionalEmail,
      productName: this.appointment.productName,
      scheduledAt: dateTime
    };

    // console.log(this.appointment)
    this.appointmentService.create(this.appointment)
      .subscribe(() => {
        this.router.navigate(['/appointments']);
      });

  }

}