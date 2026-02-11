package com.muskan.Hospital.Management.dto;

import lombok.Data;

import java.time.LocalDateTime;

public class AppointmentResponseDto {
    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;
    private DoctorResponseDto doctor;
//    private PatientResponseDto patient;
}
