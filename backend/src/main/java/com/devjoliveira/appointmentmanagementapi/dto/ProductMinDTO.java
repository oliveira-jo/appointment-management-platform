package com.devjoliveira.appointmentmanagementapi.dto;

import java.math.BigDecimal;

import com.devjoliveira.appointmentmanagementapi.domain.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductMinDTO(
        @NotBlank(message = "Name is required") String name,
        @NotNull(message = "Duration in seconds is required") @Positive Long durationInSeconds,
        @NotNull(message = "Price is required") @Positive BigDecimal price) {

    public ProductMinDTO(Product entity) {
        this(entity.getName(), entity.getDurationInSeconds().toSeconds(), entity.getPrice());
    }
}
