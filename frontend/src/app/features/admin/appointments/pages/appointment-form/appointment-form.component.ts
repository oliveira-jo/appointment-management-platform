import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerResponse } from '../../../customers/customer-modal';
import { CustomerService } from '../../../customers/customer.service';
import { ProductResponse } from '../../../products/product-modal';
import { ProductService } from '../../../products/product.service';
import { ProfessioanlResponse } from '../../../professionals/professional-model';
import { ProfessionalService } from '../../../professionals/professional.service';
import { AppointmentRequest } from '../../appointment-model';
import { AppointmentService } from '../../appointment.service';

declare var bootstrap: any;

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

  successMessage = '';
  errorMessage = '';

  successToast: any;
  errorToast: any;

  date = '';
  time = '13:00';

  appointment: AppointmentRequest = { customerEmail: '', professionalEmail: '', productName: '', scheduledAt: '' };

  constructor(
    private appointmentService: AppointmentService,
    private customerService: CustomerService,
    private professionalService: ProfessionalService,
    private productService: ProductService,
    private router: Router,

    private route: ActivatedRoute,
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
    const dateTime = formattedDate + " " + this.time + ":00";

    this.appointment = {
      customerEmail: this.appointment.customerEmail,
      professionalEmail: this.appointment.professionalEmail,
      productName: this.appointment.productName,
      scheduledAt: dateTime
    };

    if (!this.date || !this.time) {
      this.showError('Erro ao agendar. Tente novamente.');
      return;
    }

    this.appointmentService.create(this.appointment)
      .subscribe({
        next: () => {
          this.showSuccess('Agendamento realizado com sucesso!');

          setTimeout(() => {
            this.router.navigate(['/appointments']);
          }, 2000); // espera 2 segundos

        },
        error: (err) => {
          this.showError('Erro ao agendar. Tente novamente.' + err.error.message);
        }
      });

  }

  ngAfterViewInit() {
    const successEl = document.getElementById('successToast');
    const errorEl = document.getElementById('errorToast');

    this.successToast = new (window as any).bootstrap.Toast(successEl, {
      delay: 6000
    });

    this.errorToast = new (window as any).bootstrap.Toast(errorEl, {
      delay: 4000
    });
  }

  showSuccess(message: string) {
    this.successMessage = message;
    this.successToast.show();
  }

  showError(message: string) {
    this.errorMessage = message;
    this.errorToast.show();
  }

}