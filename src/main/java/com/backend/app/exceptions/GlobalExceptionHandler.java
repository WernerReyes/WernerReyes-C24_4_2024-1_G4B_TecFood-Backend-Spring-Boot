package com.backend.app.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentTypeMismatchException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid value for parameter " + ex.getName() + ": " + ex.getValue());
        error.put("status", HttpStatus.BAD_REQUEST.name());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDefinitionException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(InvalidDefinitionException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid definition: " + ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.name());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid JSON input: " + ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.name());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

