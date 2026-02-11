package com.muskan.Hospital.Management.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"patient","doctor"})
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
    private Doctors doctor;

}
