package com.devjoliveira.appointmentmanagementapi.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devjoliveira.appointmentmanagementapi.dto.AppointmentDTO;
import com.devjoliveira.appointmentmanagementapi.dto.AppointmentMinDTO;
import com.devjoliveira.appointmentmanagementapi.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

  private final AppointmentService appointmentService;

  public AppointmentController(AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  @GetMapping("/day/{day}")
  public ResponseEntity<List<AppointmentDTO>> findAppointmentsByDay(
      @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate day) {
    return ResponseEntity.ok().body(appointmentService.findAppointmentsByDay(day));
  }

  @GetMapping("/professional/{id}")
  public ResponseEntity<List<AppointmentDTO>> findAppointmentsByProfessionalId(@PathVariable UUID id) {
    return ResponseEntity.ok().body(appointmentService.findAppointmentsByProfessionalId(id));
  }

  @GetMapping("/professional/{id}/day/{day}")
  public ResponseEntity<List<AppointmentDTO>> findAppointmentsByProfessionalIdAndDay(@PathVariable UUID id,
      @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate day) {
    return ResponseEntity.ok().body(appointmentService.findAppointmentsByProfessionalIdAndDay(id, day));
  }

  @GetMapping
  public ResponseEntity<Page<AppointmentDTO>> findAll(Pageable pageable) {
    return ResponseEntity.ok().body(appointmentService.findAllPaged(pageable));
  }

  @PostMapping
  public ResponseEntity<AppointmentDTO> save(@RequestBody @Valid AppointmentMinDTO request) {

    AppointmentDTO appointmentDTO = appointmentService.createAppointment(request.customerEmail(),
        request.professionalEmail(),
        request.productName(), request.scheduledAt());

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(appointmentDTO.id()).toUri();

    return ResponseEntity.created(uri).body(appointmentDTO);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    appointmentService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
