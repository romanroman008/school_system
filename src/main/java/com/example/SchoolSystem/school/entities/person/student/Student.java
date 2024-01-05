package com.example.SchoolSystem.school.entities.person.student;


import com.example.SchoolSystem.school.entities.person.Address;
import com.example.SchoolSystem.school.entities.person.IPerson;
import com.example.SchoolSystem.school.entities.person.PersonInformation;
import com.example.SchoolSystem.school.entities.schoolClass.Grade;
import com.example.SchoolSystem.school.entities.schoolClass.SchoolClass;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Table(name = "student")
@Getter
public class Student implements IPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_information_id", referencedColumnName = "id")
    private PersonInformation personInformation;
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "school_class_id", referencedColumnName = "id")
    private SchoolClass schoolClass;
    @Setter
    private Grade grade;
    private boolean isAssignedToClass = false;


    public Student(PersonInformation personInformation, Address address, Grade grade) {
        this.personInformation = personInformation;
        this.address = address;
        this.grade = grade;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        isAssignedToClass = true;
        this.schoolClass = schoolClass;
    }

    public String getSchoolClass() {
        if (schoolClass == null)
            return "Not assigned";
        else
            return schoolClass.getTotalGrade();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getFullName() {
        return personInformation.getFirstName() + " " + personInformation.getLastName();
    }




}
