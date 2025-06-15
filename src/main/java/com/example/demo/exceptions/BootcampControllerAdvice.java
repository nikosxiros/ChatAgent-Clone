package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BootcampControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleConflict(Exception e){

        BootcmampErrorEntity errorEntity= new BootcmampErrorEntity();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if(e instanceof BootcampException){
            httpStatus = ((BootcampException) e).getHttpStatus();
        }
        errorEntity.setErrorCode(httpStatus.value());
        errorEntity.setErrorDescription(httpStatus.name());
        errorEntity.setMessage(e.getMessage());

        return ResponseEntity.status(httpStatus).body(errorEntity);
    }
}

