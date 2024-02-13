package com.example.SchoolSystem.school.schoolClass;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;


@Getter
public enum Grade {
    I(1),
    II(2),
    III(3),
    IV(4),
    V(5),
    VI(6),
    VII(7),
    VIII(8);
    private final int value;

    Grade(int value) {
        this.value = value;
    }


    public static Optional<Grade> valueOf(int value){
       return Arrays.stream(values())
               .filter(grade -> grade.value == value)
               .findFirst();
    }


    public static int getHighestValue(){
        return Arrays
                .stream(values())
                .map(grade -> grade.value)
                .max(Comparator.comparingInt(Integer::valueOf))
                .orElseThrow(() -> new NoSuchElementException("Grades are somehow empty"));

    }

    public static Grade getHighestGrade(){
        return Arrays
                .stream(values())
                .max(Comparator.comparingInt(grade -> grade.value))
                .orElseThrow(() -> new NoSuchElementException("Grades are somehow empty"));
    }

    public static int getLowestValue(){
        return Arrays
                .stream(values())
                .map(grade -> grade.value)
                .min(Comparator.comparingInt(Integer::valueOf))
                .orElseThrow(() -> new NoSuchElementException("Grades are somehow empty"));
    }

}
