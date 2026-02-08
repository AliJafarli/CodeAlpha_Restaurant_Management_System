package com.codealpha.restaurant.exception;

public class DuplicateSkuException extends RuntimeException {
    public DuplicateSkuException(String message) {
        super(message);
    }
}
