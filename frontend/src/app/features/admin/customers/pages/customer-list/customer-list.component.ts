import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CustomerRequest, CustomerResponse } from '../../customer-modal';
import { CustomerService } from '../../customer.service';
import { Page } from '../../../appointments/appointment-model';

declare var bootstrap: any;

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './customer-list.component.html'
})
export class CustomerListComponent implements OnInit {

  customers: CustomerResponse[] = [];
  customer: CustomerRequest = { name: '', email: '', phone: '' };

  page?: Page<CustomerResponse>;
  currentPage = 0
  pageSize = 10

  searchEmail = '';
  successMessage = '';
  errorMessage = '';
  successToast: any;
  errorToast: any;

  selectedCustomerId: string | null = null;
  editingId: string | null = null;

  constructor(private customerService: CustomerService) { }

  ngOnInit(): void {
    this.loadCustomers();
  }

  loadCustomers() {
    this.customerService.getAllPaged(this.currentPage, this.pageSize)
      .subscribe((data: any) => {
        this.page = data
        this.customers = data.content

        const list = this.customers;
        this.customers = list.filter(p => p.role !== 'ROLE_ADMIN');
      });
  }

  search() {
    if (!this.searchEmail) {
      this.loadCustomers();
      return;
    }

    this.customerService.getByEmail(this.searchEmail)
      .subscribe((data: any) => {
        this.customers = [data as CustomerResponse];
      });
  }

  save(form: any) {

    if (form.invalid) {
      this.showError('Preencha os campos obrigatórios!');
      return;
    }

    if (this.editingId) {
      this.customerService.update(this.editingId, this.customer)
        .subscribe({
          next: () => {
            this.showSuccess('Cliente salvo com sucesso!');
            this.afterSave(form);
          },
          error: (err) => {
            this.showError('Erro ao salvar o cliente. Tente novamente.');
          }
        });

    } else {
      this.customerService.create(this.customer)
        .subscribe({
          next: () => {
            this.showSuccess('Cliente salvo com sucesso!');
            this.afterSave(form);
          },
          error: (err) => {
            this.showError('Erro ao salvar o cliente. Tente novamente.');
          }

        });
    }
  }

  afterSave(form: any) {
    this.loadCustomers();
    form.reset();

    this.customer = { name: '', email: '', phone: '' };
    this.editingId = null;

    const modal = bootstrap.Modal.getInstance(
      document.getElementById('customerModal')
    );
    modal.hide();
  }

  change(id: string) {
    this.customerService.getById(id)
      .subscribe((data) => {

        this.customer = {
          name: data.name,
          email: data.email,
          phone: data.phone
        };

        this.editingId = id;

        const modal = new bootstrap.Modal(
          document.getElementById('customerModal')
        );

        modal.show(this.successMessage);

      });
  }

  formatPhone() {
    let phone = this.customer.phone.replace(/\D/g, '');

    if (phone.length > 11) phone = phone.slice(0, 11);

    phone = phone
      .replace(/^(\d{2})(\d)/g, "($1) $2")
      .replace(/(\d)(\d{4})$/, "$1-$2");

    this.customer.phone = phone;
  }

  delete() {
    if (!this.selectedCustomerId) return;

    this.customerService.delete(this.selectedCustomerId)
      .subscribe(() => {

        this.loadCustomers();

        const modal = bootstrap.Modal.getInstance(
          document.getElementById('deleteModal')
        );

        modal.hide();

      });
  }

  openDeleteModal(id: string) {
    this.selectedCustomerId = id;

    const modal = new bootstrap.Modal(
      document.getElementById('deleteModal')
    );

    modal.show();
  }

  ngAfterViewInit() {
    const successEl = document.getElementById('successToast');
    const errorEl = document.getElementById('errorToast');

    if (successEl) {
      this.successToast = new bootstrap.Toast(successEl, { delay: 6000 });
    }

    if (errorEl) {
      this.errorToast = new bootstrap.Toast(errorEl, { delay: 4000 });
    }
  }

  showSuccess(message: string) {
    this.successMessage = message;
    if (this.successToast) {
      this.successToast.show();
    }
  }

  showError(message: string) {
    this.errorMessage = message;
    this.errorToast.show();
  }

  nextPage() {
    if (!this.page?.last) {
      this.currentPage++
      this.loadCustomers()
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--
      this.loadCustomers()
    }
  }

}