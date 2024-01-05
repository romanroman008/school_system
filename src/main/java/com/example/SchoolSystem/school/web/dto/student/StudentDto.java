package com.example.SchoolSystem.school.web.dto.student;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record StudentDto (Long id, String firstName, String lastName, String className){}
