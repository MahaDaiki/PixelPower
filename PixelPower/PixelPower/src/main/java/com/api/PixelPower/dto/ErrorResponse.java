package com.api.PixelPower.dto;

import lombok.*;

import java.time.LocalDateTime;




@Data

public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private int status;
    private String errorCode;
    private String errorDescription;
    private String path;

    public ErrorResponse(LocalDateTime timestamp, String message, int status,
                         String errorCode, String errorDescription, String path) {
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.path = path;
    }
}
