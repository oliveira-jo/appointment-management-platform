package com.devjoliveira.appointmentmanagementapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devjoliveira.appointmentmanagementapi.doc.AuthenticationControllerDoc;
import com.devjoliveira.appointmentmanagementapi.dto.AuthResponseDTO;
import com.devjoliveira.appointmentmanagementapi.dto.LoginRequestDTO;
import com.devjoliveira.appointmentmanagementapi.dto.UserDTO;
import com.devjoliveira.appointmentmanagementapi.security.CustomUserDetails;
import com.devjoliveira.appointmentmanagementapi.security.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController implements AuthenticationControllerDoc {

  private final JwtUtil jwtUtil;

  private final AuthenticationManager authManager;

  public AuthenticationController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
    this.jwtUtil = jwtUtil;
    this.authManager = authenticationManager;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> doLogin(@RequestBody @Valid LoginRequestDTO request) {
    try {

      Authentication authentication = authManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.email(), request.password()));

      CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
      AuthResponseDTO tokenResponse = jwtUtil.generateToken(userDetails.getId(), userDetails.getUsername());
      return ResponseEntity.ok().body(tokenResponse);

    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSIONAL', 'CUSTOMER')")
  @GetMapping
  public ResponseEntity<UserDTO> getUserLogged(Authentication authentication) {

    UserDTO userDetails = (UserDTO) authentication.getPrincipal();

    return ResponseEntity.ok().body(userDetails);

  }
}