package com.martinlacorrona.ryve.api.exception;

import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public class RestException extends RuntimeException {

    private HttpStatus httpStatus;
    private String[] args;

    public RestException(HttpStatus httpStatus, ErrorMessage errorMessage, String[] args) {
        super(errorMessage.getMessage());
        this.httpStatus = httpStatus;
        this.args = args;
    }

    public RestException(HttpStatus httpStatus, ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.httpStatus = httpStatus;
        this.args = new String[]{""};
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format(super.getMessage(), args);
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return null;
    }
}
