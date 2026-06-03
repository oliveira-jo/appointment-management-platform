package com.devjoliveira.appointmentmanagementapi.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devjoliveira.appointmentmanagementapi.domain.Appointment;
import com.devjoliveira.appointmentmanagementapi.domain.Product;
import com.devjoliveira.appointmentmanagementapi.domain.User;
import com.devjoliveira.appointmentmanagementapi.dto.AppointmentResponseDTO;
import com.devjoliveira.appointmentmanagementapi.dto.MetricsResponseDTO;
import com.devjoliveira.appointmentmanagementapi.enums.AppointmentStatus;
import com.devjoliveira.appointmentmanagementapi.repository.AppointmentRepository;
import com.devjoliveira.appointmentmanagementapi.repository.ProductRepository;
import com.devjoliveira.appointmentmanagementapi.repository.UserRepository;
import com.devjoliveira.appointmentmanagementapi.service.exceptions.AppointmentDateInPastException;
import com.devjoliveira.appointmentmanagementapi.service.exceptions.ProfessionalResourceNotAvaliable;
import com.devjoliveira.appointmentmanagementapi.service.exceptions.ResourceNotFoundException;

@Service
public class AppointmentService {

  private final AppointmentRepository appointmentRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  public AppointmentService(AppointmentRepository appointmentRepository,
      ProductRepository productRepository,
      UserRepository userRepository) {
    this.appointmentRepository = appointmentRepository;
    this.productRepository = productRepository;
    this.userRepository = userRepository;
  }

  @Transactional(readOnly = true)
  public List<AppointmentResponseDTO> findAppointmentsByDay(LocalDate day) {

    LocalDateTime startOfDay = day.atStartOfDay();
    LocalDateTime endOfDay = day.atTime(LocalTime.MAX);

    return appointmentRepository.findByScheduledAtBetween(startOfDay, endOfDay).stream()
        .map(AppointmentResponseDTO::new)
        .toList();

  }

  @Transactional(readOnly = true)
  public Page<AppointmentResponseDTO> findAllPaged(Pageable pageable) {

    Page<Appointment> appointments = appointmentRepository.findAll(pageable);
    return appointments.map(AppointmentResponseDTO::new);
  }

  @Transactional(readOnly = true)
  public MetricsResponseDTO getMetrics() {

    LocalDate today = LocalDate.now();
    LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    LocalDate lastDayOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    // convert LocalDate -> LocalDateTime
    LocalDateTime start = firstDayOfWeek.atStartOfDay();
    LocalDateTime end = lastDayOfWeek.atTime(23, 59, 59);

    // Total Revenue Today
    List<Appointment> todayRevenueAppointments = appointmentRepository
        .findByScheduledAtBetween(LocalDate.now().atTime(00, 00, 00),
            LocalDate.now().atTime(23, 59, 59));

    BigDecimal todayRevenue = BigDecimal.ZERO;
    for (Appointment a : todayRevenueAppointments) {
      todayRevenue = todayRevenue.add(a.getProduct().getPrice());
    }

    // Other metrics
    Integer todayAppointments = this.findAppointmentsByDay(LocalDate.now()).size();
    Integer weekAppointments = appointmentRepository.findByScheduledAtBetween(start, end).size();
    Integer totalAppointments = this.appointmentRepository.findAll().size();

    return new MetricsResponseDTO(todayAppointments, weekAppointments, todayRevenue, totalAppointments);
  }

  @Transactional
  public AppointmentResponseDTO createAppointment(String customerEmail, String professionalEmail, String productName,
      LocalDateTime scheduledAt) {

    User customer = userRepository.findByEmail(customerEmail).orElseThrow(
        () -> new ResourceNotFoundException("Customer not found with email: " + customerEmail));
    User professional = userRepository.findByEmail(professionalEmail).orElseThrow(
        () -> new ResourceNotFoundException("Professional not found with email: " + professionalEmail));
    Product product = productRepository.findByName(productName).orElseThrow(
        () -> new ResourceNotFoundException("Product not found with name: " + productName));

    // Verify if date is no in the past
    if (scheduledAt.isBefore(LocalDateTime.now())) {
      throw new AppointmentDateInPastException(
          "Agendamento não pode ser feito em data passada - AppointmentDateInPastException");
    }

    // calculate the end time of the appointment based on the product duration
    LocalDateTime endsAt = scheduledAt.plusSeconds(product.getDurationInSeconds().toSeconds());

    // verify if the professional is available at the requested time -
    // if there are any conflicting appointments
    List<Appointment> conflicts = appointmentRepository.findConflictingAppointments(professional.getId(), scheduledAt,
        endsAt);

    if (!conflicts.isEmpty()) {
      throw new ProfessionalResourceNotAvaliable(
          "Professional: " + professional.getName() + " is not available at the requested time: " + scheduledAt);
    }

    // create and save the appointment
    Appointment appointment = new Appointment();
    appointment.setCustomer(customer);
    appointment.setProfessional(professional);
    appointment.setProduct(product);
    appointment.setScheduledAt(scheduledAt);
    appointment.setEndsAt(endsAt);
    appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);

    var fromDB = appointmentRepository.save(appointment);

    return new AppointmentResponseDTO(fromDB);
  }

  @Transactional
  public void delete(UUID id) {

    Appointment fromDB = appointmentRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Appointment with id " + id + " not found"));

    fromDB.setAppointmentStatus(AppointmentStatus.CANCELLED);
    this.appointmentRepository.save(fromDB);

  }

  @Transactional
  public void updateCompletedAppointments() {
    appointmentRepository.markAsCompleted(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
  }

  @Transactional
  public void deleteOldAppointments() {
    LocalDateTime limit = LocalDateTime.now().minusMonths(6);
    appointmentRepository.deleteOlderThan(limit);
  }

}
