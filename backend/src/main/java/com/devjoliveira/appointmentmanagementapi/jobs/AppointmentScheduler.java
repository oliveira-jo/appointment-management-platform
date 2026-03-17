package com.devjoliveira.appointmentmanagementapi.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.devjoliveira.appointmentmanagementapi.service.AppointmentService;

@Component
public class AppointmentScheduler {

  private final AppointmentService service;

  public AppointmentScheduler(AppointmentService service) {
    this.service = service;

  }

  // Update Appointment Status to COMPLETED
  @Scheduled(fixedRate = 30000) // a cada 1 minuto
  public void run() {
    service.updateCompletedAppointments();
  }

  // Clean old datas
  @Scheduled(cron = "0 0 1 * * *")
  public void cleanup() {
    service.deleteOldAppointments();
  }
}