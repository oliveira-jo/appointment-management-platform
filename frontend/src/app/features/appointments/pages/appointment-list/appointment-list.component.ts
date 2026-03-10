import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../../services/appointment.service';
import { Appointment } from '../../models/Appointment';
import { Page } from '../../models/Page';

@Component({
  standalone: true,
  selector: 'app-appointment-list',
  templateUrl: './appointment-list.component.html'
})
export class AppointmentListComponent implements OnInit {

  appointments: Appointment[] = [];
  page?: Page<Appointment>;

  currentPage = 0
  pageSize = 20

  constructor(private service: AppointmentService) { }


  ngOnInit() {
    this.loadAppointments()
  }

  loadAppointments() {
    this.service
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
}
