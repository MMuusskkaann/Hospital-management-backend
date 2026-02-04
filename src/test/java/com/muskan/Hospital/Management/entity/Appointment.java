package com.muskan.Hospital.Management.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column
    private String reason;

    @ManyToOne  //many appointment to patient one
    @JoinColumn(name =  "patient_id",nullable = false) //patient is required but not nullable
    private Patient patient;

    @ManyToOne
    @JoinColumn(nullable = false)
    private doctors doctor;
}
