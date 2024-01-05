package com.example.SchoolSystem.school.exceptions;

public class SchoolClassHasToManyLessonsPerWeekException extends RuntimeException {
    public SchoolClassHasToManyLessonsPerWeekException(String message) {
        super(message);
    }
}
