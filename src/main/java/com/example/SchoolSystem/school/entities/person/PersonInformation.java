package com.example.SchoolSystem.school.entities.person;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "pesel")})
@Table(name = "person_information")
public class PersonInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "holder_id")
    private Long id;
    private String firstName;
    private String lastName;
    private String pesel;
    private LocalDate birthday;
    private String phone;
    private String email;




    private PersonInformation(Builder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.pesel = builder.pesel;
        this.birthday = builder.birthday;
        this.phone = builder.phone;
        this.email = builder.email;
    }
   // @Table(uniqueConstraints = {@UniqueConstraint(columnNames = "pesel")})
    @Getter
    public static class Builder {
        private String firstName;
        private String lastName;
        private String pesel;
        private LocalDate birthday;
        private String phone;
        private String email;

        public Builder() {
        }

        public Builder setFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder setPesel(String pesel) {
            this.pesel = pesel;
            return this;
        }

        public Builder setBirthday(LocalDate birthday) {
           // this.birthday = birthday;
            this.birthday = LocalDate.now();
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public PersonInformation build(){
            return new PersonInformation(this);
        }
    }
}
