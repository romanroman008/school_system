package com.example.SchoolSystem.school.entities.person;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
public interface IPerson {
    Long getId();
    String getFullName();

}
