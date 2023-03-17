package com.danielbenami.playermaker.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class ApiError {


    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    Map<String,String>  validationErrors = new HashMap<>();
    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}