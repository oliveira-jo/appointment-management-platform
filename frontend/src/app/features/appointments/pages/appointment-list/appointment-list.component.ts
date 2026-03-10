import { Component, OnInit } from '@angular/core';
import { AppointmentService, Appointment } from '../../services/appointment.service';

@Component({
  standalone: true,
  selector: 'app-appointment-list',
  templateUrl: './appointment-list.component.html'
})
export class AppointmentListComponent implements OnInit {

  appointments: Appointment[] = [];

  constructor(private service: AppointmentService) { }

  ngOnInit(): void {
    this.service.getAll().subscribe(data => {
      this.appointments = data;
    });
  }
}