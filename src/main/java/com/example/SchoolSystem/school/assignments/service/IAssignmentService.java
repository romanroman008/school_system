package com.example.SchoolSystem.school.assignments.service;

import com.example.SchoolSystem.school.assignments.IAssignment;

import java.util.List;
import java.util.Map;

public interface IAssignmentService {

    Map<String,List<String>> assign(List<? extends IAssignment> assignments);


}
