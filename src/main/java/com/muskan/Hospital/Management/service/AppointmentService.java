package com.muskan.Hospital.Management.service;
//import org.modelmapper.ModelMapper;
import com.muskan.Hospital.Management.dto.AppointmentResponseDto;
import com.muskan.Hospital.Management.dto.CreateAppointmentRequestDto;
import com.muskan.Hospital.Management.entity.Appointment;
import com.muskan.Hospital.Management.entity.Doctors;
import com.muskan.Hospital.Management.entity.Patient;
import com.muskan.Hospital.Management.repository.AppointmentRepository;
import com.muskan.Hospital.Management.repository.DoctorRepository;
import com.muskan.Hospital.Management.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto createAppointmentRequestDto) {
        Long doctorId = createAppointmentRequestDto.getDoctorId();
        Long patientId = createAppointmentRequestDto.getPatientId();

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
        Doctors doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));
//        Appointment appointment = Appointment.builder()
//                .reason(createAppointmentRequestDto.getReason())
//                .appointmentTime(createAppointmentRequestDto.getAppointmentTime())
//                .build();

        Appointment appointment = new Appointment();
        appointment.setReason(createAppointmentRequestDto.getReason());
        appointment.setAppointmentTime(createAppointmentRequestDto.getAppointmentTime());


        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        patient.getAppointments().add(appointment); // to maintain consistency

        appointment = appointmentRepository.save(appointment);
        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long newDoctorId){
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctors doctor = doctorRepository.findById(newDoctorId).orElseThrow();

        appointment.setDoctor(doctor); // JPA automatically updates because of @Transactional
        return appointment;
    }

    @PreAuthorize("hasRole('ADMIN') OR (hasRole('DOCTOR') AND #doctorId == authentication.principal.id)")
    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
        Doctors doctor = doctorRepository.findById(doctorId).orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }
}
