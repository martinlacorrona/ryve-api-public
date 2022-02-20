package com.martinlacorrona.ryve.api.config;

import com.martinlacorrona.ryve.api.exception.RestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestException> exceptionHandler(RestException ex){
        return new ResponseEntity<>(ex, ex.getHttpStatus());
    }
}
