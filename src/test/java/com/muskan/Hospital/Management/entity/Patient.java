package com.muskan.Hospital.Management.entity;

import jakarta.persistence.*;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@ToString
@Table(
        name = "patient_table",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_email", columnNames = {"email"}),
                @UniqueConstraint(name = "unique_patient_name_birthDate", columnNames = {"name","birthDate"}),
        },
        indexes = {
                @Index(name = "idx_patient_birth_data",columnList = "birthDate")
        }
)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name  = "patient_name")
    private String name;

    @ToString.Exclude
    private LocalDate birthDate;

    private String email;

    private String gender;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    //    @Override
//    public String toString() {
//        return "Patient{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", birthDate=" + birthDate +
//                ", email='" + email + '\'' +
//                ", gender='" + gender + '\'' +
//                '}';
//    }
}
