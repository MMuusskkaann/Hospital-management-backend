package com.muskan.Hospital.Management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"patient", "doctor"})
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column
    private String reason;

    // Many appointments belong to one patient
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnore
    private Patient patient;

    // Many appointments belong to one doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonIgnore   // 🔥 add this if Doctor also has List<Appointment>
    private Doctors doctor;
}