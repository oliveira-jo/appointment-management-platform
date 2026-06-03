package com.devjoliveira.appointmentmanagementapi.dto;

import java.util.UUID;

import com.devjoliveira.appointmentmanagementapi.domain.User;

public record UserResponseDTO(
                UUID id,
                String name,
                String phone,
                String email,
                String role,
                String password) {

        public UserResponseDTO(User customer) {
                this(customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail(),
                                customer.getRole().name(), customer.getPassword());
        }
}
