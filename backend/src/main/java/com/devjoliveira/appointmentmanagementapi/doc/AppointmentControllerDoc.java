package com.devjoliveira.appointmentmanagementapi.doc;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import com.devjoliveira.appointmentmanagementapi.controller.exceptions.StandardError;
import com.devjoliveira.appointmentmanagementapi.dto.AppointmentDTO;
import com.devjoliveira.appointmentmanagementapi.dto.AppointmentMinDTO;
import com.devjoliveira.appointmentmanagementapi.dto.MetricsDTO;

// @formatter:off

@Tag(name = "Appointments", description = "Operations for registration, consultation, updating, deletion and filtering of appointments")
public interface AppointmentControllerDoc {



  // Find All Appointments Pageable
  @Operation(
      summary = "Find All Appointments Pageable",
      description = "List appointments in a pageable format, allowing for pagination and sorting",
      responses = {
              @ApiResponse(responseCode = "200", description = "List of appointments returned successfully")
      })
  ResponseEntity<Page<AppointmentDTO>> findAll(
      @Parameter(description = "Optional filters for searching appointments", required = false) 
      Pageable pageable
      );

  // Find By DAY
  @Operation(
    summary = "Find Appointment by Day",
    description = "Returns a specific appointment by the day it is scheduled",
    responses = {
            @ApiResponse(responseCode = "200", description = "Appointments found"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Appointments not found or invalid ID",
                    content = @Content(schema = @Schema(implementation =  StandardError.class))
            )})
  ResponseEntity<List<AppointmentDTO>> findAppointmentsByDay(
      @Parameter(description = "Day of the appointments to find", example = "dd-MM-yyyy", required = true)
      LocalDate day);

      // Find By ID
  @Operation(
          summary = "Find Metrics",
          description = "Returns metrics for a specific appointment",
          responses = {
                  @ApiResponse(responseCode = "200", description = "Appointment metrics found")})
  ResponseEntity<MetricsDTO> GetMetrics();


  // Save
  @Operation(
    summary = "Save Appointment", description = "Create a new appointment in the system",
    responses = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Appointment created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error or business rule violation",
                    content = @Content(schema = @Schema(implementation = StandardError.class))
            )})
  ResponseEntity<AppointmentDTO> save(
      @RequestBody
      @Valid
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Necessary data to register a customer", required = true,
            content = @Content(schema = @Schema(implementation = AppointmentMinDTO.class),
            examples = @ExampleObject(
                    name = "Appointment valid",
                    value = """
                            {
                              "customerEmail" : "customer@example.com",
                              "professionalEmail": "professional@example.com",
                              "productName": "Product Name",
                              "scheduledAt": "2023-10-10T10:00:00",
                              "status": "SCHEDULED"
                            }
                            """ )))
      AppointmentMinDTO productMinRequest);

  // Delete
  @Operation(
    summary = "Delete Appointment", description = "Remove an appointment from the system",
    responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Appointment deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error or business rule violation",
                    content = @Content(schema = @Schema(implementation = StandardError.class))
            )})
  ResponseEntity<Void> deleteById(
    @Parameter(description = "ID of the appointment to be deleted", required = true)
    Long id);

}

// @formatter:on