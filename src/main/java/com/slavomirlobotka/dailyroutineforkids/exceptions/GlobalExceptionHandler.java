package com.slavomirlobotka.dailyroutineforkids.exceptions;

import com.slavomirlobotka.dailyroutineforkids.dtos.ErrorResponseDTO;
import com.slavomirlobotka.dailyroutineforkids.dtos.RegistrationErrorDTO;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
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

  @ExceptionHandler(value = {DailyRoutineNotFound.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected ErrorResponseDTO handleDailyRoutineNotFound(DailyRoutineNotFound ex) {

    return handleException(
        ex, HttpStatus.NOT_FOUND, "Sorry, the requested resource was not found.");
  }

  @ExceptionHandler(value = {DailyRoutineInternalServerError.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ErrorResponseDTO handleDailyRoutineInternalServerError(
      DailyRoutineInternalServerError ex) {

    return handleException(
        ex,
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Oops! Something went wrong. Our team has been notified about this issue. Please try again later.");
  }

  @ExceptionHandler(value = {DailyRoutineUnauthorized.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  protected ErrorResponseDTO handleDailyRoutineUnauthorized(DailyRoutineUnauthorized ex) {

    return handleException(
        ex, HttpStatus.UNAUTHORIZED, "Access denied. Please log in to access this resource.");
  }

  @ExceptionHandler(value = {DailyRoutineForbidden.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  protected ErrorResponseDTO handleDailyRoutineForbidden(DailyRoutineForbidden ex) {

    return handleException(
        ex,
        HttpStatus.FORBIDDEN,
        "Access forbidden. You don't have permission to access this resource.");
  }

  @ExceptionHandler(value = {DailyRoutineBadRequest.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponseDTO handleDailyRoutineBadRequest(DailyRoutineBadRequest ex) {

    return handleException(
        ex, HttpStatus.BAD_REQUEST, "Invalid request. Please check your input and try again.");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public RegistrationErrorDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = processFieldErrors(ex);

    return new RegistrationErrorDTO(HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now(), errors);
  }

  private Map<String, String> processFieldErrors(MethodArgumentNotValidException ex) {
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

    Map<String, String> errors = new LinkedHashMap<>();
    fieldOrder.forEach(
        field -> {
          if (fieldErrors.containsKey(field)) {
            errors.put(field, customErrorMessages.getOrDefault(field, fieldErrors.get(field)));
          }
        });

    return errors;
  }

  protected ErrorResponseDTO handleException(
      Exception ex, HttpStatus status, String defaultMessage) {
    String message =
        ex.getMessage() == null || ex.getMessage().isBlank() ? defaultMessage : ex.getMessage();

    return createErrorResponse(status, message);
  }

  private String formatDate(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMM yyyy, HH:mm:ss", Locale.ENGLISH);

    return dateFormat.format(date);
  }

  private ErrorResponseDTO createErrorResponse(HttpStatus status, String message) {

    return new ErrorResponseDTO(status.value() + " " + status.getReasonPhrase().toUpperCase(), message, formatDate(new Date()));
  }
}
