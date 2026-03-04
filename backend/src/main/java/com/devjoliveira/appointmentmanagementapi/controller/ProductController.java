package com.devjoliveira.appointmentmanagementapi.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devjoliveira.appointmentmanagementapi.dto.ProductDTO;
import com.devjoliveira.appointmentmanagementapi.dto.ProductMinDTO;
import com.devjoliveira.appointmentmanagementapi.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<List<ProductDTO>> findAll() {
    return ResponseEntity.ok().body(productService.findAll());
  }

  @PostMapping
  public ResponseEntity<ProductDTO> save(@RequestBody @Valid ProductMinDTO request) {

    ProductDTO productDTO = productService.save(request);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(productDTO.id()).toUri();

    return ResponseEntity.created(uri).body(productDTO);

  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDTO> change(@PathVariable UUID id, @RequestBody @Valid ProductMinDTO request) {
    return ResponseEntity.ok().body(productService.change(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    productService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
