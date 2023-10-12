package ru.liga.orderservice.exception.handler;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.liga.orderservice.exception.DataNotFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @AllArgsConstructor
    static class ApiErrorResponse {
        private final LocalDateTime timestamp;
        private final String message;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleDataNotFoundException(DataNotFoundException e) {
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        return new ResponseEntity<>(new ApiErrorResponse(LocalDateTime.now(), e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
