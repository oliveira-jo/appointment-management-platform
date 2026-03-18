package com.devjoliveira.appointmentmanagementapi.controller;

import java.net.URI;
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

import com.devjoliveira.appointmentmanagementapi.dto.UserDTO;
import com.devjoliveira.appointmentmanagementapi.dto.UserMinDTO;
import com.devjoliveira.appointmentmanagementapi.enums.UserRole;
import com.devjoliveira.appointmentmanagementapi.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final UserService userService;

  public CustomerController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<Page<UserMinDTO>> findAll(Pageable pageable) {
    return ResponseEntity.ok().body(userService.findAllPaged(pageable));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL', 'CUSTOMER')")
  @GetMapping("/email/{email}")
  public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
    return ResponseEntity.ok().body(userService.findByEmail(email));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL', 'CUSTOMER')")
  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
    return ResponseEntity.ok().body(userService.findById(id));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL')")
  @PostMapping
  public ResponseEntity<UserDTO> save(@RequestBody @Valid UserMinDTO request) {

    UserDTO customerDTO = userService.save(request, UserRole.ROLE_CUSTOMER);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(customerDTO.id()).toUri();

    return ResponseEntity.created(uri).body(customerDTO);

  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL')")
  @PutMapping("/{id}")
  public ResponseEntity<UserDTO> change(@PathVariable UUID id, @RequestBody @Valid UserMinDTO request) {
    return ResponseEntity.ok().body(userService.change(id, request));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
