package com.example.SchoolSystem.school.schoolClass;


import com.example.SchoolSystem.school.teacher.Teacher;
import com.example.SchoolSystem.school.student.Student;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.*;

@Entity
@Table(name = "school_class")
@Getter
@NoArgsConstructor
public class SchoolClass {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private Grade grade;
    @Enumerated(EnumType.STRING)
    private AlphabeticalGrade alphabeticalGrade;
    @OneToMany(mappedBy = "schoolClass",cascade = {CascadeType.MERGE,CascadeType.PERSIST},fetch = FetchType.EAGER)
    private List<Student> students;

    @Getter
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "school_class_id", referencedColumnName = "id")
    private final Set<SchoolSubject> schoolSubjects = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(
            name = "teaching_classes",
            joinColumns = @JoinColumn(name = "schoolClass_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private final Set<Teacher> teachers = new HashSet<>();



    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "school_class_id", referencedColumnName = "id")
    List<SubjectToTeacherAssignment> subjectsWithTeachers = new ArrayList<>();



    public boolean everySubjectHasTeacherAssigned = false;
    public boolean isGraduated;
    private boolean isFull;

    public SchoolClass(Grade grade, AlphabeticalGrade alphabeticalGrade, List<Student> students) {
        this.grade = grade;
        this.alphabeticalGrade = alphabeticalGrade;
        this.students = students;
    }

    public void addSubjects(List<SchoolSubject> subjects){
        schoolSubjects.addAll(subjects);
        subjects.forEach(schoolSubject -> subjectsWithTeachers.add(new SubjectToTeacherAssignment(schoolSubject)));
    }
    public void assignTeacherToSubject(SchoolSubject subject, Teacher teacher) {

        teachers.add(teacher);
        teacher.addTeachingClass(this);
        SubjectToTeacherAssignment assignment = findAssignmentForGivenSchoolSubject(subject);
        assignment.setTeacher(teacher);



        checkIfEverySubjectHasTeacherAssigned();
    }

    private SubjectToTeacherAssignment findAssignmentForGivenSchoolSubject(SchoolSubject schooLSubject){
       return subjectsWithTeachers
                .stream()
                .filter(assignment -> assignment.getSchoolSubject().equals(schooLSubject))
                .findFirst()
                .orElseThrow();
    }


    private void checkIfEverySubjectHasTeacherAssigned(){
        everySubjectHasTeacherAssigned = subjectsWithTeachers.stream().allMatch(SubjectToTeacherAssignment::isAssigned);
    }

    public void increaseGrade() {
        int gradeValue = grade.getValue();
        if (gradeValue < Grade.getHighestValue()) {
            grade = Grade.valueOf(++gradeValue).get();
        }
    }

    public void decreaseGrade() {
        int gradeValue = grade.getValue();
        if (gradeValue < Grade.getLowestValue()) {
            grade = Grade.valueOf(--gradeValue).get();
        }
    }


    public String getTotalGrade() {
        return grade.toString() + alphabeticalGrade.toString();
    }


    public void addStudent(Student student) {

        if(student.getGrade().equals(grade)){
            student.setSchoolClass(this);
            students.add(student);
        }else{
            throw new IllegalArgumentException(
                    String.format("Given student grade %s does not equals this class grade %s",
                    student.getGrade(),
                    grade)
            );
        }

    }

    public List<SubjectToTeacherAssignment> getSubjectsWithAssignedTeachers(){
        return subjectsWithTeachers;
    }




}
