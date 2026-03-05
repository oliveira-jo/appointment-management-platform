package com.devjoliveira.appointmentmanagementapi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.devjoliveira.appointmentmanagementapi.domain.Appointment;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;

public record AppointmentMinDTO(
                UUID id,
                @NotBlank(message = "Customer email is required") String customerEmail,
                @NotBlank(message = "Professional email is required") String professionalEmail,
                @NotBlank(message = "Product name is required") String productName,
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime scheduledAt,
                String status) {

        public AppointmentMinDTO(Appointment entity) {
                this(entity.getId(), entity.getCustomer().getEmail(),
                                entity.getProfessional().getEmail(),
                                entity.getProduct().getName(), entity.getScheduledAt(),
                                entity.getAppointmentStatus().toString());
        }

}
