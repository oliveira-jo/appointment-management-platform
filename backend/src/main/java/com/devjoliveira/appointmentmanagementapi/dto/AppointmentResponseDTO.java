package com.devjoliveira.appointmentmanagementapi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.devjoliveira.appointmentmanagementapi.domain.Appointment;

public record AppointmentResponseDTO(
                UUID id,
                String customerName,
                String professionalName,
                String productName,
                LocalDateTime scheduledAt,
                String status) {

        public AppointmentResponseDTO(Appointment entity) {
                this(entity.getId(), entity.getCustomer().getName(),
                                entity.getProfessional().getName(),
                                entity.getProduct().getName(), entity.getScheduledAt(),
                                entity.getAppointmentStatus().toString());
        }

}
