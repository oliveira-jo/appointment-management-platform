package com.devjoliveira.appointmentmanagementapi.dto;

import java.util.UUID;

import com.devjoliveira.appointmentmanagementapi.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserMinDTO(

                UUID id,
                @NotBlank(message = "Name is required") String name,
                @NotBlank(message = "Phone is required") String phone,
                @NotBlank(message = "Email is required") @Email String email,
                String password,
                String role) {

        public UserMinDTO(User customer) {
                this(customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail(),
                                customer.getPassword(),
                                customer.getRole().toString());
        }
}
