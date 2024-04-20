package com.slavomirlobotka.dailyroutineforkids.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  // Handler for IllegalArgumentException
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgumentException(
      IllegalArgumentException ex, WebRequest request) {
    // You can log the exception and request details here if needed
    // For example: logger.error("Illegal argument exception: {}", ex.getMessage());

    // Construct an error response structure
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getDescription(false));

    // Return response entity with the custom error response body and HTTP status code
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  // Define a ErrorResponse class to standardize error responses
  private static class ErrorResponse {
    private int statusCode;
    private String message;
    private String description;

    public ErrorResponse(int statusCode, String message, String description) {
      this.statusCode = statusCode;
      this.message = message;
      this.description = description;
    }

    // Getters are required for serialization of the response body
    public int getStatusCode() {
      return statusCode;
    }

    public String getMessage() {
      return message;
    }

    public String getDescription() {
      return description;
    }
  }
}
