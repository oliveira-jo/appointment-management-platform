package com.devjoliveira.appointmentmanagementapi.dto;

import com.devjoliveira.appointmentmanagementapi.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserMinDTO(
                @NotBlank(message = "Name is required") String name,
                @NotBlank(message = "Phone is required") String phone,
                @NotBlank(message = "Email is required") @Email String email,
                String role) {

        public UserMinDTO(User customer) {
                this(customer.getName(), customer.getPhone(), customer.getEmail(), customer.getRole().toString());
        }
}
