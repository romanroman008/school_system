package com.example.SchoolSystem.school.timetable.exceptions;

public class NoTeacherAvailableException extends RuntimeException{
    public NoTeacherAvailableException() {
    }

    public NoTeacherAvailableException(String message) {
        super(message);
    }
}
