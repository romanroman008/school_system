package com.example.SchoolSystem.school.exceptions;

public class NoTeacherAvailableException extends RuntimeException{
    public NoTeacherAvailableException() {
    }

    public NoTeacherAvailableException(String message) {
        super(message);
    }
}
