package com.devjoliveira.appointmentmanagementapi.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.devjoliveira.appointmentmanagementapi.domain.Product;

public record ProductDTO(
    UUID id,
    String name,
    Long durationInSeconds,
    BigDecimal price) {

  public ProductDTO(Product product) {
    this(product.getId(), product.getName(), product.getDurationInSeconds().toSeconds(), product.getPrice());
  }

}
