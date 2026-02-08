package com.codealpha.restaurant.advice;

import com.codealpha.restaurant.exception.DuplicateSkuException;
import com.codealpha.restaurant.service.exception.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateSkuException.class)
    public ResponseEntity<?> handleDuplicateSku(DuplicateSkuException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "error", "DUPLICATE_SKU",
                        "message", ex.getMessage()
                )
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> handleConflict(ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }
}
