package com.example.SchoolSystem.school.teacher;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TeacherRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String IDNumber;
    @NotBlank
    private String birthday;
    private String phone;
    @Email
    private String email;
    @NotBlank
    private String city;
    private String street;
    @Positive
    @NotNull
    private int houseNumber;
    @Positive
    private int flatNumber;
    @Positive
    private float salary;
    @Positive
    private int hoursPerWeek;
}
