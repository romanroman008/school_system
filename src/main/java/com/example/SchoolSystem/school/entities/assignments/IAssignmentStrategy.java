package com.example.SchoolSystem.school.entities.assignments;

import com.example.SchoolSystem.school.web.assignments.IAssignment;

import java.util.List;
import java.util.Map;

public interface IAssignmentStrategy {

    Map<String,List<String>> assign(List<? extends IAssignment> assignments);
}
