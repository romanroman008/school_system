package com.example.SchoolSystem.school.student;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentRequest {

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
    private int gradeToBirthdayDeflection;



}
