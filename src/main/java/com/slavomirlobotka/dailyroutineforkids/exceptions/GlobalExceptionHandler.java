package com.slavomirlobotka.dailyroutineforkids.exceptions;

import com.slavomirlobotka.dailyroutineforkids.dtos.ErrorResponseDTO;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

  private final List<String> fieldOrder = List.of("firstName", "surname", "email", "password");
  private final Map<String, String> customErrorMessages = new LinkedHashMap<>();

  public GlobalExceptionHandler() {
    customErrorMessages.put("firstName", "Please enter your first name");
    customErrorMessages.put("surname", "Please enter your surname");
    customErrorMessages.put(
        "email",
        "Please enter a valid email address in proper format with all lowercase letters. Example: 'name@domain.com'");
    customErrorMessages.put(
        "password",
        "Password must have at least 8 symbols and contain at least one uppercase letter and one digit. Example: 'Password123'");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorResponseDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new LinkedHashMap<>();

    Map<String, String> fieldErrors =
        ex.getBindingResult().getAllErrors().stream()
            .filter(error -> error instanceof FieldError)
            .map(error -> (FieldError) error)
            .collect(
                Collectors.toMap(
                    FieldError::getField,
                    FieldError::getDefaultMessage,
                    (existing, replacement) -> existing,
                    LinkedHashMap::new));

    fieldOrder.forEach(
        field -> {
          if (fieldErrors.containsKey(field)) {
            errors.put(field, customErrorMessages.getOrDefault(field, fieldErrors.get(field)));
          }
        });

    return new ErrorResponseDTO(HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now(), errors);
  }
}
