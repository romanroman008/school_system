package com.example.SchoolSystem.school.exceptions;

import com.example.SchoolSystem.school.student.StudentRequest;

public class StudentsAgeException extends RuntimeException{
    StudentRequest studentRequest;

    public StudentsAgeException(String message, StudentRequest studentRequest) {
        super(message);
        this.studentRequest = studentRequest;
    }
}
