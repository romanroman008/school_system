package com.example.SchoolSystem.school.teacher;


import com.example.SchoolSystem.school.person.Address;
import com.example.SchoolSystem.school.person.PersonInformation;
import com.example.SchoolSystem.school.schoolClass.SchoolClass;
import com.example.SchoolSystem.school.schoolSubject.SchoolSubject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@NoArgsConstructor
@Entity
public class Teacher{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_information_id", referencedColumnName = "id")
    private PersonInformation personInformation;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @Setter
    private float salary;

    @Setter
    private int hoursPerWeek;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private final Set<SchoolSubject> teachingSubjects = new HashSet<>();


    @ManyToMany(mappedBy = "teachers")
    private final Set<SchoolClass> teachingClasses = new HashSet<>();

    public Teacher(PersonInformation personInformation, Address address, float salary, int hoursPerWeek) {
        this.personInformation = personInformation;
        this.address = address;
        this.salary = salary;
        this.hoursPerWeek = hoursPerWeek;
    }

    public String getFullName(){
        return personInformation.getFirstName() + " " + personInformation.getLastName();
    }


    public void addTeachingClass(SchoolClass schoolClass){
        teachingClasses.add(schoolClass);
    }
    public void removeTeachingClass(SchoolClass schoolClass){
        teachingClasses.remove(schoolClass);
    }

    public void addTeachingSubject(SchoolSubject subject){
        this.teachingSubjects.add(subject);
    }
}
