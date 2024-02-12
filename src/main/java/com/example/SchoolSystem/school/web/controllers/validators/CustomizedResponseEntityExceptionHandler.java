package com.example.SchoolSystem.school.web.controllers.validators;

import com.example.SchoolSystem.school.exceptions.NotEnoughTeachersException;
import com.example.SchoolSystem.school.exceptions.SchoolClassHasToManyLessonsPerWeekException;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.HireRecommendation;
import com.example.SchoolSystem.school.web.exceptions.ErrorDetails;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityExistsException.class)
    public final ResponseEntity<Object> handleEntityExistsException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotEnoughTeachersException.class)
    public final ResponseEntity<Object> handleNotEnoughTeachersException(NotEnoughTeachersException ex, WebRequest request){
        return new ResponseEntity<>(ex.getHireRecommendation(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SchoolClassHasToManyLessonsPerWeekException.class)
    public final ResponseEntity<Object> handleTooManyLessonsException(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


}
