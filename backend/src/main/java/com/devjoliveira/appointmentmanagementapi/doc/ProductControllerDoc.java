
package com.devjoliveira.appointmentmanagementapi.doc;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.devjoliveira.appointmentmanagementapi.controller.exceptions.StandardError;
import com.devjoliveira.appointmentmanagementapi.dto.ProductDTO;
import com.devjoliveira.appointmentmanagementapi.dto.ProductMinDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

// @formatter:off

@Tag(name = "Products", description = "Operations for registration, consultation, updating, deletion products")
public interface ProductControllerDoc {

  
        // Find All
        @Operation(
                summary = "Find all products",
                description = "List products",
                responses = {
                        @ApiResponse(responseCode = "200", description = "List of products returned successfully")
                })
        ResponseEntity<List<ProductDTO>> findAll();

        // Find All Products Pageable
        @Operation(
                summary = "Find All Products Pageable",
                description = "List products in a pageable format, allowing for pagination and sorting",
                responses = {
                        @ApiResponse(responseCode = "200", description = "List of products returned successfully")
                })
        ResponseEntity<Page<ProductDTO>> findAllPaged(
                @Parameter(description = "Optional filters for searching products", required = false) 
                Pageable pageable
                );

        // Find By ID
        @Operation(
                summary = "Find Product by ID",
                description = "Returns a specific product",
                responses = {
                        @ApiResponse(responseCode = "200", description = "Product found"),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Product not found or invalid ID",
                                content = @Content(schema = @Schema(implementation =  StandardError.class))
                        )})
        ResponseEntity<ProductDTO> findById(
            @Parameter(description = "Product ID", example = "550e8400-e29b-41d4-a716-446655440000", required = true) UUID id);


        // Find By Name
        @Operation(
                summary = "Search Product by Name",
                description = "Returns a product",
                responses = {
                        @ApiResponse(responseCode = "200", description = "Product found"),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Product not found or invalid ID",
                                content = @Content(schema = @Schema(implementation =  StandardError.class))
                        )})
        ResponseEntity<List<ProductDTO>> findByName(
            @Parameter(description = "Product Name", example = "Product Name", required = true) String name);



        // Save
        @Operation(
            summary = "Save product", description = "Create a new product in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Product created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error or business rule violation",
                            content = @Content(schema = @Schema(implementation = StandardError.class))
                    )})
        ResponseEntity<ProductDTO> save(
                        @RequestBody
                        @Valid
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                              description = "Necessary data to register a customer", required = true,
                              content = @Content(schema = @Schema(implementation = ProductMinDTO.class),
                              examples = @ExampleObject(
                                      name = "Customer valid",
                                      value = """
                                              {
                                                "name" : "Product Name",
                                                "durationInSeconds": 3600,
                                                "price": 79.99
                                              }
                                              """ )))
                        ProductMinDTO productMinRequest);

        // Change
        @Operation(
            summary = "Change product", description = "Update an existing product in the system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation error or business rule violation",
                            content = @Content(schema = @Schema(implementation = StandardError.class))
                    )})
        ResponseEntity<ProductDTO> change(
                        @Parameter(description = "ID of the product to be changed", required = true)
                        UUID id,
                        @RequestBody @Valid
                        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                              description = "Necessary data to update a product",
                              required = true,
                              content = @Content(schema = @Schema(implementation = ProductMinDTO.class),
                              examples = @ExampleObject(
                                      name = "Product valid",
                                      value = """
                                              {
                                                "name" : "Product Name",
                                                "durationInSeconds": 3600,
                                                "price": 99.99
                                              }
                                              """)))
                                ProductMinDTO productMinRequest);

        // Delete
        @Operation(
                summary = "Delete product", description = "Remove a product from the system",
                responses = {
                        @ApiResponse(
                                responseCode = "204",
                                description = "Product deleted successfully"
                        ),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Validation error or business rule violation",
                                content = @Content(schema = @Schema(implementation = StandardError.class))
                        )})
        ResponseEntity<Void> delete(
                @Parameter(description = "ID of the product to be deleted", required = true)
                UUID id);

}

// @formatter:on