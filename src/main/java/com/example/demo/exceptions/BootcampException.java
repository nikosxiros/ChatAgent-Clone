package com.example.demo.exceptions;


import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BootcampException extends Exception {

    private HttpStatus httpStatus;


    public BootcampException(String message){
        super(message);
    }

public BootcampException(HttpStatus httpStatus,String message){
        super(message);
        this.httpStatus=httpStatus;
}

public BootcampException(String message, Throwable cause){
        super(message, cause);
}

public BootcampException(Throwable cause){
        super(cause);
}
}
