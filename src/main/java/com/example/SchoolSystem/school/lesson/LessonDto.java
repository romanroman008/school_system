package com.example.SchoolSystem.school.lesson;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public record LessonDto (Long id, long sortingNumber, int number, String day, String schoolClass, String subject, String teacher) {}
