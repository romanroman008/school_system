package com.example.SchoolSystem.school.assignments.strategies;

import com.example.SchoolSystem.school.assignments.IAssignment;

import java.util.List;
import java.util.Map;

public interface IAssignmentStrategy {

    Map<String,List<String>> assign(List<? extends IAssignment> assignments);
}
