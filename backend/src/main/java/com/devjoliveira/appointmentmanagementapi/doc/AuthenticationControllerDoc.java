package com.devjoliveira.appointmentmanagementapi.doc;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.Parameter;

import com.devjoliveira.appointmentmanagementapi.controller.exceptions.StandardError;
import com.devjoliveira.appointmentmanagementapi.dto.AuthResponseDTO;
import com.devjoliveira.appointmentmanagementapi.dto.LoginRequestDTO;

import com.devjoliveira.appointmentmanagementapi.dto.UserResponseDTO;

// @formatter:off

@Tag(name = "Authentication", description = "Operations for user authentication")
public interface AuthenticationControllerDoc {

  @Operation(
    summary = "Login", description = "Logged user in the system",
    responses = {
      @ApiResponse(
              responseCode = "200",
              description = "Logged user successfully",
              content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))
      ),
      @ApiResponse(
              responseCode = "401",
              description = "Unauthorized - Invalid credentials",
              content = @Content(schema = @Schema(implementation = StandardError.class))
      )})
      ResponseEntity<AuthResponseDTO> doLogin(
          @RequestBody
          @Valid
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Necessary data to register a student", required = true,
                content = @Content(schema = @Schema(implementation = LoginRequestDTO.class),
                examples = @ExampleObject(
                        name = "Login valid",
                        value = """
                                {
                                      "email" : "email@example.com",
                                      "password": "password123",
                                }
                                """  )))
          
        LoginRequestDTO request);


  @Operation(
    summary = "Search User Logged",
    description = "Returns the user logged in the system",
    responses = {
      @ApiResponse(responseCode = "200", description = "Student found"),
      @ApiResponse(
              responseCode = "401",
              description = "Unauthorized - Invalid credentials",
              content = @Content(schema = @Schema(implementation =  StandardError.class))
      )})
  
      ResponseEntity<UserResponseDTO> getUserLogged(
          @Parameter(description = "Authentication object containing the logged user's details", 
          required = true)
      Authentication authentication);

}

// @formatter:on