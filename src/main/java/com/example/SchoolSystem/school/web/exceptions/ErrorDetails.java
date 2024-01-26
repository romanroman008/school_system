package com.example.SchoolSystem.school.web.exceptions;

import java.time.LocalDate;
import java.util.List;

public class ErrorDetails {
    private LocalDate timestamp;
    private String message;
    private String details;
    private List<Object> invalidObjects;

    public ErrorDetails(LocalDate timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
