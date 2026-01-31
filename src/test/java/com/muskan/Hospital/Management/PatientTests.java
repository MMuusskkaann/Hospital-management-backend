package com.muskan.Hospital.Management;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.muskan.Hospital.Management.entity.Patient;
import com.muskan.Hospital.Management.repository.PatientRepository;
import com.muskan.Hospital.Management.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
public class PatientTests {

    @Autowired

    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Test
    public  void testPatientRepository(){
       List<Patient> patientList = patientRepository.findAll();
       List<Patient> patients = patientRepository.findAll();
       System.out.println("Number of patients: " + patients.size());
       patients.forEach(System.out::println);

    }

    @Test
    public void testTransactionMethods(){

         Patient patient= patientService.getPatientById(1L);

        System.out.println(patient);

//         Patient patient= patientService.getPatientById(1L);
//
//        System.out.println(patient);

//        Patient patient = patientRepository.findByName("William Brown");
//        List<Patient> patientList = patientRepository.findByBirthDateOrEmail(LocalDate.of(1988,7,30), "benjamin.thomas@example.com");

//        List<Patient> patientList = patientRepository.findByNameContaining("en");

//        List<Patient> patientList = patientRepository.findByGender("Female");

        List<Patient> patientList = patientRepository.findByBornAfterDate(LocalDate.of(1987,3,10));

        for(Patient patient : patientList){
            System.out.println(patient);
        }

         Patient patient= patientService.getPatientById(1L);

        System.out.println(patient);

    }
}
