package com.example.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootcmampErrorEntity {
    private String message;
    private Integer errorCode;
    private String errorDescription;
}
