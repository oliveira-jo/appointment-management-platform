package com.devjoliveira.appointmentmanagementapi.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devjoliveira.appointmentmanagementapi.doc.ProductControllerDoc;
import com.devjoliveira.appointmentmanagementapi.dto.ProductResponseDTO;
import com.devjoliveira.appointmentmanagementapi.dto.ProductRequestDTO;
import com.devjoliveira.appointmentmanagementapi.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController implements ProductControllerDoc {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<ProductResponseDTO>> findAll() {
    return ResponseEntity.ok().body(productService.findAll());
  }

  @GetMapping
  public ResponseEntity<Page<ProductResponseDTO>> findAllPaged(Pageable pageable) {
    return ResponseEntity.ok().body(productService.findAllPaged(pageable));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL', 'CUSTOMER')")
  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> findById(@PathVariable UUID id) {
    return ResponseEntity.ok().body(productService.findById(id));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL', 'CUSTOMER')")
  @GetMapping("/name/{name}")
  public ResponseEntity<List<ProductResponseDTO>> findByName(@PathVariable String name) {
    return ResponseEntity.ok().body(productService.findByName(name));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL')")
  @PostMapping
  public ResponseEntity<ProductResponseDTO> save(@RequestBody @Valid ProductRequestDTO request) {

    ProductResponseDTO productDTO = productService.save(request);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(productDTO.id()).toUri();

    return ResponseEntity.created(uri).body(productDTO);

  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL')")
  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> change(@PathVariable UUID id,
      @RequestBody @Valid ProductRequestDTO request) {
    return ResponseEntity.ok().body(productService.change(id, request));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    productService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
