
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

@Tag(name = "Customers", description = "Operations for registration, consultation, updating, deletion customers")
public interface CustomerControllerDoc {

  
        // Find All
        @Operation(
                summary = "Find all customers",
                description = "List customers",
                responses = {
                        @ApiResponse(responseCode = "200", description = "List of customers returned successfully")
                })
        ResponseEntity<List<UserMinDTO>> findAll();

        // Find All Customers Pageable
        @Operation(
                summary = "Find All Customers Pageable",
                description = "List customers in a pageable format, allowing for pagination and sorting",
                responses = {
                        @ApiResponse(responseCode = "200", description = "List of customers returned successfully")
                })
        ResponseEntity<Page<UserMinDTO>> findAll(
                 @Parameter(description = "Optional filters for searching customers", required = false) 
                Pageable pageable
                );


        // Find By Email
        @Operation(
                summary = "Search Customer by Email",
                description = "Returns a customer",
                responses = {
                        @ApiResponse(responseCode = "200", description = "Customer found"),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Customer not found or invalid ID",
                                content = @Content(schema = @Schema(implementation =  StandardError.class))
                        )})
        ResponseEntity<UserDTO> findByEmail(
            @Parameter(description = "Customer Email", example = "joao@email.com", required = true) String email);


        // Find By ID
        @Operation(
                summary = "Find Customer by ID",
                description = "Returns a specific customer",
                responses = {
                        @ApiResponse(responseCode = "200", description = "Customer found"),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Customer not found or invalid ID",
                                content = @Content(schema = @Schema(implementation =  StandardError.class))
                        )})
        ResponseEntity<UserDTO> findById(
            @Parameter(description = "Customer ID", example = "550e8400-e29b-41d4-a716-446655440000", required = true) UUID id);



        // Save
        @Operation(
            summary = "Save customer", description = "Create a new customer in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Customer created successfully"
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
                              description = "Necessary data to register a customer", required = true,
                              content = @Content(schema = @Schema(implementation = UserMinDTO.class),
                              examples = @ExampleObject(
                                      name = "Customer valid",
                                      value = """
                                              {
                                                    "name" : "João da Silva",
                                                    "phone": "48991645543",
                                                    "email": "joao@email.com",
                                                    "password": "password123",
                                                    "role": "CUSTOMER",
                                              }
                                              """ )))
                        UserMinDTO userMinRequest);

        // Change
        @Operation(
            summary = "Change customer", description = "Update an existing customer in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Customer updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error or business rule violation",
                            content = @Content(schema = @Schema(implementation = StandardError.class))
                    )})
        ResponseEntity<UserDTO> change(
                        @Parameter(description = "ID of the customer to be changed", required = true)
                        UUID id,
                        @RequestBody @Valid
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                              description = "Necessary data to update a customer",
                              required = true,
                              content = @Content(schema = @Schema(implementation = UserMinDTO.class),
                              examples = @ExampleObject(
                                      name = "Customer valid",
                                      value = """
                                              {
                                                    "name" : "João da Silva Oliveira",
                                                    "phone": "48991645543",
                                                    "email": "joao@email.com",
                                                    "password": "password123",
                                                    "role": "CUSTOMER",
                                              }
                                              """)))
                                UserMinDTO userMinRequest);

        // Delete
        @Operation(
                summary = "Delete customer", description = "Remove a customer from the system",
                responses = {
                        @ApiResponse(
                                responseCode = "204",
                                description = "Customer deleted successfully"
                        ),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Validation error or business rule violation",
                                content = @Content(schema = @Schema(implementation = StandardError.class))
                        )})
        ResponseEntity<Void> delete(
                @Parameter(description = "ID of the customer to be deleted", required = true)
                UUID id);

}

// @formatter:on