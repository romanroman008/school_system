package com.example.SchoolSystem.school.exceptions;


import com.example.SchoolSystem.school.timetable.timetablePlainObjects.HireRecommendation;
import lombok.Getter;

@Getter
public class NotEnoughTeachersException extends RuntimeException{

    private HireRecommendation hireRecommendation;

    public NotEnoughTeachersException() {
    }

    public NotEnoughTeachersException(HireRecommendation hireRecommendation){
        this.hireRecommendation = hireRecommendation;
    }

    public NotEnoughTeachersException(String message) {
        super(message);
    }



    public void setHireRecommendation(HireRecommendation hireRecommendation){
        this.hireRecommendation = hireRecommendation;
    }


}
