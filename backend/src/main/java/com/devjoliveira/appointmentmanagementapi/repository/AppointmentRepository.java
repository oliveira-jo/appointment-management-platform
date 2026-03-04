package com.devjoliveira.appointmentmanagementapi.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devjoliveira.appointmentmanagementapi.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

  @Query("""
          SELECT appointment FROM Appointment appointment
          WHERE appointment.professional.id = :professionalId
          AND appointment.status <> 'CANCELLED'
          AND appointment.scheduledAt < :end
          AND appointment.endsAt > :start
      """)
  List<Appointment> findConflictingAppointments(UUID professionalId, LocalDateTime start, LocalDateTime end);

  List<Appointment> findByProfessionalId(UUID professionalId);

  List<Appointment> findByProfessionalIdAndScheduledAtBetween(UUID professionalId, LocalDateTime start,
      LocalDateTime end);

  List<Appointment> findByScheduledAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

  Page<Appointment> findAll(Pageable pageable);

}
