package com.devjoliveira.appointmentmanagementapi.dto;

import java.util.UUID;

import com.devjoliveira.appointmentmanagementapi.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(

                UUID id,
                @NotBlank(message = "Name is required") String name,
                @NotBlank(message = "Phone is required") @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?9\\d{4}-?\\d{4}$", message = "Invalid phone format") String phone,
                @NotBlank(message = "Email is required") @Email String email,
                String password,
                String role) {

        public UserRequestDTO(User customer) {
                this(customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail(),
                                customer.getPassword(),
                                customer.getRole().toString());
        }
}
