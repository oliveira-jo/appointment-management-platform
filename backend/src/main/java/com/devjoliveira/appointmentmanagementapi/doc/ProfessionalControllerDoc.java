
package com.devjoliveira.appointmentmanagementapi.doc;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.devjoliveira.appointmentmanagementapi.controller.exceptions.StandardError;
import com.devjoliveira.appointmentmanagementapi.dto.UserDTO;
import com.devjoliveira.appointmentmanagementapi.dto.UserMinDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

// @formatter:off

@Tag(name = "Professionals", description = "Operations for registration, consultation, updating, deletion professionals")
public interface ProfessionalControllerDoc {

  
        // Find All
        @Operation(
                summary = "Find all professionals",
                description = "List professionals",
                responses = {
                        @ApiResponse(responseCode = "200", description = "List of professionals returned successfully")
                })
        ResponseEntity<List<UserMinDTO>> findAll();

        // Find All Professionals Pageable
        @Operation(
                summary = "Find All Professionals Pageable",
                description = "List professionals in a pageable format, allowing for pagination and sorting",
                responses = {
                        @ApiResponse(responseCode = "200", description = "List of professionals returned successfully")
                })
        ResponseEntity<Page<UserMinDTO>> findAll(
                 @Parameter(description = "Optional filters for searching professionals", required = false) 
                Pageable pageable
                );


        // Find By Email
        @Operation(
                summary = "Search Professional by Email",
                description = "Returns a professional",
                responses = {
                        @ApiResponse(responseCode = "200", description = "Professional found"),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Professional not found or invalid ID",
                                content = @Content(schema = @Schema(implementation =  StandardError.class))
                        )})
        ResponseEntity<UserDTO> findByEmail(
            @Parameter(description = "Professional Email", example = "joao@email.com", required = true) String email);

        // Find By ID
        @Operation(
                summary = "Find Professional by ID",
                description = "Returns a specific professional",
                responses = {
                        @ApiResponse(responseCode = "200", description = "Professional found"),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Professional not found or invalid ID",
                                content = @Content(schema = @Schema(implementation =  StandardError.class))
                        )})
        ResponseEntity<UserDTO> findById(
            @Parameter(description = "Professional ID", example = "550e8400-e29b-41d4-a716-446655440000", required = true) UUID id);


        // Save
        @Operation(
            summary = "Save professional", description = "Create a new professional in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Professional created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error or business rule violation",
                            content = @Content(schema = @Schema(implementation = StandardError.class))
                    )})
        ResponseEntity<UserDTO> save(
                        @RequestBody
                        @Valid
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                              description = "Necessary data to register a professional", required = true,
                              content = @Content(schema = @Schema(implementation = UserMinDTO.class),
                              examples = @ExampleObject(
                                      name = "Professional valid",
                                      value = """
                                              {
                                                    "name" : "Leandro Bueno Massoco",
                                                    "phone": "48991645543",
                                                    "email": "leandro@email.com",
                                                    "password": "password123",
                                                    "role": "PROFESSIONAL",
                                              }
                                              """ )))
                        UserMinDTO userMinRequest);

        // Change
        @Operation(
            summary = "Change professional", description = "Update an existing professional in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Professional updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error or business rule violation",
                            content = @Content(schema = @Schema(implementation = StandardError.class))
                    )})
        ResponseEntity<UserDTO> change(
                        @Parameter(description = "ID of the professional to be changed", required = true)
                        UUID id,
                        @RequestBody @Valid
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                              description = "Necessary data to update a professional",
                              required = true,
                              content = @Content(schema = @Schema(implementation = UserMinDTO.class),
                              examples = @ExampleObject(
                                      name = "Professional valid",
                                      value = """
                                              {
                                                    "name" : "Leandro Massoco",
                                                    "phone": "48991645543",
                                                    "email": "leandro@email.com",
                                                    "password": "password123",
                                                    "role": "PROFESSIONAL",
                                              }
                                              """)))
                                UserMinDTO userMinRequest);

        // Delete
        @Operation(
                summary = "Delete professional", description = "Remove a professional from the system",
                responses = {
                        @ApiResponse(
                                responseCode = "204",
                                description = "Professional deleted successfully"
                        ),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Validation error or business rule violation",
                                content = @Content(schema = @Schema(implementation = StandardError.class))
                        )})
        ResponseEntity<Void> delete(
                @Parameter(description = "ID of the professional to be deleted", required = true)
                UUID id);

}

// @formatter:on