package com.muskan.Hospital.Management.service;

import com.muskan.Hospital.Management.entity.Appointment;
import com.muskan.Hospital.Management.entity.Doctors;
import com.muskan.Hospital.Management.entity.Patient;
import com.muskan.Hospital.Management.repository.AppointmentRepository;
import com.muskan.Hospital.Management.repository.DoctorRepository;
import com.muskan.Hospital.Management.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public void createNewAppointment(Appointment appointment,Long doctorId,Long patientId){
        Doctors doctors = doctorRepository.findById(doctorId).orElseThrow();
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        if(appointment.getId() != null) throw new IllegalArgumentException("Appointment should have");

        appointment.setPatient(patient);
        appointment.setDoctor(doctors);

        appointmentRepository.save(appointment);

    }
}
