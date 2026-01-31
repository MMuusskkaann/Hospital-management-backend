package com.muskan.Hospital.Management;

import com.muskan.Hospital.Management.entity.Patient;
import com.muskan.Hospital.Management.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class PatientTests {

    @Autowired

    private PatientRepository patientRepository;
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
    }
}
