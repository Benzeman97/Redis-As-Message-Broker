package com.benz.redis.api.exception;

import com.benz.redis.api.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DataNotFoundExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> toResponse(DataNotFoundException exception)
    {
        ErrorMessage errorMessage=new ErrorMessage(404,exception.getMessage(),"www.benz.com");
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
