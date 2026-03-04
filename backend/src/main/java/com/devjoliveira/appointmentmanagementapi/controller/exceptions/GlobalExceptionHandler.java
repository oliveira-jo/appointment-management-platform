package com.devjoliveira.appointmentmanagementapi.controller.exceptions;

import java.time.Instant;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devjoliveira.appointmentmanagementapi.service.exceptions.DatabaseException;
import com.devjoliveira.appointmentmanagementapi.service.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<StandardError> entityNotFoundException(ResourceNotFoundException e,
      HttpServletRequest request) {

    return buildErrorResponse(HttpStatus.NOT_FOUND, "Resource not found", e.getMessage(), request.getRequestURI());

  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest request) {

    return buildErrorResponse(HttpStatus.BAD_REQUEST, "Database exception.", e.getMessage(), null);

  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException e,
      HttpServletRequest request) {

    return buildErrorResponse(HttpStatus.BAD_REQUEST, "Database exception.", e.getMessage(), request.getRequestURI());

  }

  private ResponseEntity<StandardError> buildErrorResponse(HttpStatus status, String error, String message,
      String path) {

    StandardError body = new StandardError(
        Instant.now(),
        status.value(),
        error,
        message,
        path);

    return ResponseEntity.status(status).body(body);
  }

}
