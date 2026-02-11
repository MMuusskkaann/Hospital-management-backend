package com.muskan.Hospital.Management;

import com.muskan.Hospital.Management.entity.Appointment;
import com.muskan.Hospital.Management.entity.Insurance;
import com.muskan.Hospital.Management.entity.Patient;
import com.muskan.Hospital.Management.service.AppointmentService;
import com.muskan.Hospital.Management.service.InsuranceService;
import org.aspectj.weaver.ast.Var;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class Insurancetest {

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testInsurance(){

        Insurance insurance= Insurance.builder()
                .policyNumber("HDFC_1234")
                .provider("HDFC")
                .validUntil(LocalDate.of(2030, 12 ,12 ))
                .build();

       Patient patient =  insuranceService.assignInsuranceToPatient(insurance, 1L);

       System.out.println(patient);

       patient = insuranceService.disaccociateInsuranceFromPatient(patient.getId());

        System.out.println(patient);
    }

        @Test
        public void testCreateAppointment() {

            // Create appointment
            Appointment appointment = Appointment.builder()
                    .appointmentTime(LocalDateTime.of(2026, 2, 14, 12, 14))
                    .reason("Cancer")
                    .build();

            // Save appointment (this will set the ID automatically)
//            appointmentService.createNewAppointment(appointment, 2L, 1L); // doctorId=2, patientId=1

            // Print appointment after creation
            System.out.println("Created Appointment: " + appointment);

            // Reassign to another doctor (use method that accepts new doctor ID)
            appointment = appointmentService.reAssignAppointmentToAnotherDoctor(appointment.getId(), 3L);

            // Print appointment after reassignment
            System.out.println("Reassigned Appointment: " + appointment);
        }


    }
