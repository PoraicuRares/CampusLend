package com.campuslend.backend.exception;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends  RuntimeException{
    private final HttpStatus httpStatus;
    protected ApiException(HttpStatus httpStatus , String message){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
