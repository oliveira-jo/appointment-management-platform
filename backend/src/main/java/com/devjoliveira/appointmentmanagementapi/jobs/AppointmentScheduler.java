package com.devjoliveira.appointmentmanagementapi.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.devjoliveira.appointmentmanagementapi.service.AppointmentService;
import com.devjoliveira.appointmentmanagementapi.service.NotificationService;

@Component
public class AppointmentScheduler {

  private final AppointmentService appointmentService;
  private final NotificationService notificationService;

  public AppointmentScheduler(AppointmentService service, NotificationService notificationService) {
    this.appointmentService = service;
    this.notificationService = notificationService;
  }

  // Update Appointment Status to COMPLETED
  // Every 60 seconds
  @Scheduled(fixedRate = 60000)
  public void run() {
    appointmentService.updateCompletedAppointments();
  }

  // Clean old datas
  // Every day at 1:00 AM
  @Scheduled(cron = "0 0 1 * * *")
  public void cleanup() {
    appointmentService.deleteOldAppointments();
  }

  // @Scheduled(fixedRate = 60000) // For testing: every 60 seconds
  @Scheduled(cron = "0 0 7 * * *")
  public void sendReminderOneDayBefore() {
    notificationService.sendReminderOneDayBefore();
  }

}