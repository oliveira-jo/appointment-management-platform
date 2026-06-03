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

import com.devjoliveira.appointmentmanagementapi.doc.ProfessionalControllerDoc;
import com.devjoliveira.appointmentmanagementapi.dto.UserResponseDTO;
import com.devjoliveira.appointmentmanagementapi.dto.UserRequestDTO;
import com.devjoliveira.appointmentmanagementapi.enums.UserRole;
import com.devjoliveira.appointmentmanagementapi.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/professionals")
public class ProfessionalController implements ProfessionalControllerDoc {

  private final UserService userService;

  public ProfessionalController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<UserRequestDTO>> findAll() {
    return ResponseEntity.ok().body(userService.findAllProfessionals());
  }

  @GetMapping
  public ResponseEntity<Page<UserRequestDTO>> findAll(Pageable pageable) {
    return ResponseEntity.ok().body(userService.findAllProfessionalsPage(pageable));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL', 'CUSTOMER')")
  @GetMapping("/email/{email}")
  public ResponseEntity<UserResponseDTO> findByEmail(@PathVariable String email) {
    return ResponseEntity.ok().body(userService.findByEmail(email));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL', 'CUSTOMER')")
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
    return ResponseEntity.ok().body(userService.findById(id));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<UserResponseDTO> save(@RequestBody @Valid UserRequestDTO request) {

    UserResponseDTO professionalDTO = userService.save(request, UserRole.ROLE_PROFESSIONAL);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(professionalDTO.id()).toUri();

    return ResponseEntity.created(uri).body(professionalDTO);

  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> change(@PathVariable UUID id, @RequestBody @Valid UserRequestDTO request) {
    return ResponseEntity.ok().body(userService.change(id, request));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
