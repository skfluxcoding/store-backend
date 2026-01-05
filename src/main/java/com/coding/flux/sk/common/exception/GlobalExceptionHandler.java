package com.coding.flux.sk.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(err -> new ValidationError(err.getField(), err.getDefaultMessage()
                ))
                .toList();
        // TODO
        // Create ApiResponse for management response body in answer application REST API
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<Object, Object>> notFoudException(
            NotFoundException ex) {
        Map<Object, Object> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        errors.put("type", "NOT_FOUND");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<Object, Object>> alreadyExistsException(
            AlreadyExistsException ex) {
        Map<Object, Object> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        errors.put("type", "ALREADY_EXISTS");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
