package org.dreamcommerce.dreamcommerce.exception.handler;

import org.dreamcommerce.dreamcommerce.dto.response.DreamCommerceResponse;
import org.dreamcommerce.dreamcommerce.exception.DreamCommerceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class DreamCommerceExceptionHandler {
    // Handle all types of Exception here

    @ExceptionHandler(DreamCommerceException.class) // This includes all exceptions that extended this class
    public ResponseEntity<?> handleDreamCommerceException(DreamCommerceException exception) {
        DreamCommerceResponse<?> response = new DreamCommerceResponse<>();
        response.setMessage(exception.getMessage());
        response.setSuccess(false);
        response.setErrors(List.of(exception.getLocalizedMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException exception) {
        DreamCommerceResponse<?> response = new DreamCommerceResponse<>();
        response.setMessage(exception.getMessage());
        response.setSuccess(false);
        response.setErrors(List.of(exception.getLocalizedMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // All Exceptions incase the one up doesn't catch any
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        DreamCommerceResponse<?> response = new DreamCommerceResponse<>();
        response.setMessage(exception.getMessage());
        response.setSuccess(false);
        response.setErrors(List.of(exception.getLocalizedMessage()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
