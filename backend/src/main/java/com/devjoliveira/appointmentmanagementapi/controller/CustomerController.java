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

import com.devjoliveira.appointmentmanagementapi.dto.UserDTO;
import com.devjoliveira.appointmentmanagementapi.dto.UserMinDTO;
import com.devjoliveira.appointmentmanagementapi.enums.UserRole;
import com.devjoliveira.appointmentmanagementapi.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final UserService customerService;

  public CustomerController(UserService customerService) {
    this.customerService = customerService;
  }

  @GetMapping
  public ResponseEntity<List<UserMinDTO>> findAll() {
    return ResponseEntity.ok().body(customerService.findAll());
  }

  @GetMapping("/{email}")
  public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
    return ResponseEntity.ok().body(customerService.findByEmail(email));
  }

  @PostMapping
  public ResponseEntity<UserDTO> save(@RequestBody @Valid UserMinDTO request) {

    UserDTO customerDTO = customerService.save(request, UserRole.ROLE_CUSTOMER);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(customerDTO.id()).toUri();

    return ResponseEntity.created(uri).body(customerDTO);

  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDTO> change(@PathVariable UUID id, @RequestBody @Valid UserMinDTO request) {
    return ResponseEntity.ok().body(customerService.change(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    customerService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
