package com.example.SchoolSystem.school.schoolClass;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum AlphabeticalGrade {
    A(1),
    B(2),
    C(3),
    D(4);

    private final int value;
    AlphabeticalGrade(int value){
        this.value = value;
    }
    public static Optional<AlphabeticalGrade> valueOf(int value){
        return Arrays.stream(values())
                .filter(grade -> grade.value == value)
                .findFirst();
    }


}
