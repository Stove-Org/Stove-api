package gg.stove.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import gg.stove.exception.DataNotFoundException;
import gg.stove.exception.DuplicatedException;
import lombok.Builder;
import lombok.Value;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, DateTimeParseException.class})
    protected ResponseEntity<ErrorResponse> badRequestHandler(RuntimeException e) {
        return errorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<ErrorResponse> forbiddenHandler(RuntimeException e) {
        return errorResponse(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    protected ResponseEntity<ErrorResponse> notFoundHandler(RuntimeException e) {
        return errorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DuplicatedException.class})
    protected ResponseEntity<ErrorResponse> conflictHandler(RuntimeException e) {
        return errorResponse(e, HttpStatus.CONFLICT);
    }

    private ResponseEntity<ErrorResponse> errorResponse(RuntimeException e, HttpStatus httpStatus) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(httpStatus.value())
            .errorMessage(e.getMessage())
            .build();

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @Value
    @Builder
    public static class ErrorResponse {
        LocalDateTime timestamp = LocalDateTime.now();
        int status;
        String errorMessage;
    }
}
