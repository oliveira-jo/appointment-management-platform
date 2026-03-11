import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../../appointment.service';
import { AppointmentRequest, AppointmentResponse, MetricsResponse, Page } from '../../appointment-model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  selector: 'app-appointment-list',
  templateUrl: './appointment-list.component.html'
})
export class AppointmentListComponent implements OnInit {

  appointments: AppointmentResponse[] = [];
  appointment: AppointmentRequest = { customerEmail: '', professionalEmail: '', productName: '', scheduledAt: '' }

  page?: Page<AppointmentResponse>;

  currentPage = 0
  pageSize = 20

  searchProfessionalName = '';
  searchCustomerlName = '';
  toastMessage = '';

  selectedAppointmentId: string | null = null;
  editingId: string | null = null;


  // pagination
  pageNumber = 0;
  totalPages = 0;
  pages: number[] = [];

  // metrics cards
  metrics = {
    today: 0,
    week: 0,
    revenueToday: 0,
    total: 0
  };

  // filter
  filter = {
    startDate: '',
    endDate: ''
  };

  constructor(private appointmentService: AppointmentService) { }


  ngOnInit() {
    this.loadAppointments()
  }

  loadAppointments() {
    this.appointmentService
      .getAll(this.currentPage, this.pageSize)
      .subscribe(response => {
        this.page = response
        this.appointments = response.content
      })
  }

  nextPage() {
    if (!this.page?.last) {
      this.currentPage++
      this.loadAppointments()
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--
      this.loadAppointments()
    }
  }

  search() {
    this.appointmentService.getByDay(this.filter.startDate)
      .subscribe(res => {
        this.appointments = res;
      });
  }

  // save(form: any) {
  //   if (this.editingId) {
  //     this.appointmentService.update(this.editingId, this.appointment)
  //       .subscribe(() => {

  //         this.toastMessage = "Customer updated successfully";

  //         this.afterSave(form);

  //       });

  //   } else {
  //     this.appointmentService.create(this.appointment)
  //       .subscribe(() => {

  //         this.toastMessage = "Customer created successfully";

  //         this.afterSave(form);

  //       });
  //   }
  // }

  // afterSave(form: any) {
  //   this.loadAppointments();

  //   form.reset();

  //   this.appointment = { customerEmail: '', professionalEmail: '', productName: '', scheduledAt: '' }

  //   this.editingId = null;

  // }

  // change(id: string) {
  //   this.appointmentService.getById(id)
  //     .subscribe((data) => {

  //       this.appointment = {
  //         customerEmail: this.appointment.customerEmail,
  //         professionalEmail: this.appointment.professionalEmail,
  //         productName: data.productName,
  //         scheduledAt: data.scheduledAt
  //       };

  //       this.editingId = id;

  //     });
  // }

  delete() {
    if (!this.selectedAppointmentId) return;

    this.appointmentService.delete(this.selectedAppointmentId)
      .subscribe(() => {

        this.loadAppointments();
        this.loadMetrics();

      });
  }

  openDeleteModal(id: string) {
    this.selectedAppointmentId = id;

  }

  clearFilter() {

    this.filter.startDate = '';
    this.filter.endDate = '';

    this.loadAppointments();
  }

  loadMetrics() {

    const metrics: MetricsResponse = { today: 1, week: 1, revenueToday: 0, total: 2 };
    this.metrics = metrics;

  }

}

