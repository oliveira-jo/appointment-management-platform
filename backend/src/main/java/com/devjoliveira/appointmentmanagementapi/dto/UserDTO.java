package com.devjoliveira.appointmentmanagementapi.dto;

import java.util.UUID;

import com.devjoliveira.appointmentmanagementapi.domain.User;

public record UserDTO(
                UUID id,
                String name,
                String phone,
                String email) {

        public UserDTO(User customer) {
                this(customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail());
        }
}
