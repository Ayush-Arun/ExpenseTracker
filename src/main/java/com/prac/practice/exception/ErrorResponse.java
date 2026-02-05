package com.prac.practice.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;
    private int status;

    public ErrorResponse(String message, LocalDateTime timestamp, int status) {
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }

    public ErrorResponse(Map<String, String> errors, LocalDateTime timestamp, int status) {
        this.errors = errors;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }
}
