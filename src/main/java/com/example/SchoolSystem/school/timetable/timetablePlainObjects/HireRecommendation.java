package com.example.SchoolSystem.school.timetable.timetablePlainObjects;


import com.example.SchoolSystem.school.timetable.timetablePlainObjects.teacher.TeacherTimetable;
import com.example.SchoolSystem.school.timetable.timetablePlainObjects.subject.SubjectTimetable;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HireRecommendation {

    @Getter
    Map<String, Integer> subjectsWithHoursNeeded = new HashMap<>();
    @Getter
    private StringBuilder message = new StringBuilder();
    boolean teachersNeeded = false;

    public boolean hasTeachersNeeded() {
        return teachersNeeded;
    }

    public HireRecommendation(List<TeacherTimetable> teachers, List<SubjectTimetable> subjects){
        resetTeachersHours(teachers);
       subjects.forEach(subject ->{
           addHoursNeeded(subject);
           subtractHours(subject,teachers);
       });

       if(checkIfAnyTeacherIsNeeded()){
           createNegativeMessage(teachers);
       }
       else{
           createPositiveMessage();
       }

    }

    private void resetTeachersHours(List<TeacherTimetable> teachers){
        teachers.forEach(TeacherTimetable::resetHours);
    }

    private void addHoursNeeded(SubjectTimetable subject){
        subjectsWithHoursNeeded.merge(subject.getName(), subject.getHoursPerWeek(),Integer::sum);
    }

    private void subtractHours(SubjectTimetable subject, List<TeacherTimetable> teachers){
        Optional<TeacherTimetable> teacher = teachers.stream()
                .filter(t -> t.getTeachingSubjects().contains(subject.getName()))
                .filter(t -> t.checkIfHasSufficientAmountOfHours(subject.getHoursPerWeek()))
                .findFirst();
        teacher.ifPresent(t -> {
            t.assignHours(subject.getHoursPerWeek());
            subjectsWithHoursNeeded.merge(subject.getName(),subject.getHoursPerWeek(),this::subtract);
        });
    }
    private void createNegativeMessage(List<TeacherTimetable> teachers){
        teachersNeeded = true;
        if(checkIfAnyTeacherIsNeeded()){
            message.append("The number of hours missing to arrange the plan: \n");
            subjectsWithHoursNeeded.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() > 0)
                            .forEach(entry -> {
                                message.append(entry.getKey());
                                message.append(" : ");
                                message.append(entry.getValue());
                                message.append("\n");
                            });

            if(checkIfAnyTeacherHasTwoSubjectQualifications(teachers)){
                message.append("Some teachers are qualified in multiple subjects, so the number of hours required in their subjects alternates. \n");
                teachers.stream()
                        .filter(teacher -> teacher.getTeachingSubjects().size() > 1)
                        .forEach(teacher -> {
                            message.append(teacher.getName());
                            message.append(": \n");
                            teacher.getTeachingSubjects().forEach(subject -> {
                                message.append(subject);
                                message.append(", ");
                            });
                        });
            }

        }
    }

    private void createPositiveMessage(){
        teachersNeeded = false;
        message.append("Everythin's tight, m sayn?");
    }



    private boolean checkIfAnyTeacherIsNeeded(){
        return subjectsWithHoursNeeded.values().stream().anyMatch(x -> x > 0);
    }

    private boolean checkIfAnyTeacherHasTwoSubjectQualifications(List<TeacherTimetable> teachers){
        return teachers.stream().anyMatch(teacher -> teacher.getTeachingSubjects().size() > 1);
    }


    private int subtract(int a, int b){
        return a - b;
    }


}
