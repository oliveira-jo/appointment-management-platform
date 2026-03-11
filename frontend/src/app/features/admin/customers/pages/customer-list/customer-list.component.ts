import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CustomerRequest, CustomerResponse } from '../../customer-modal';
import { CustomerService } from '../../customer.service';

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

  searchEmail = '';
  toastMessage = '';

  selectedCustomerId: string | null = null;
  editingId: string | null = null;

  constructor(private customerService: CustomerService) { }

  ngOnInit(): void {
    this.loadCustomers();
  }

  loadCustomers() {

    this.customerService.getAll()
      .subscribe((data: any) => {
        const list = this.customers = data as CustomerResponse[];
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
    if (this.editingId) {
      this.customerService.update(this.editingId, this.customer)
        .subscribe(() => {

          this.toastMessage = "Customer updated successfully";

          this.afterSave(form);

        });

    } else {
      this.customerService.create(this.customer)
        .subscribe(() => {

          this.toastMessage = "Customer created successfully";

          this.afterSave(form);

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

    this.showToast();
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

        modal.show();

      });
  }

  showToast() {
    const toastEl = document.getElementById('successToast');
    const toast = new bootstrap.Toast(toastEl);

    toast.show();
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

}