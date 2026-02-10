package com.muskan.Hospital.Management.service;


import com.muskan.Hospital.Management.entity.Insurance;
import com.muskan.Hospital.Management.entity.Patient;
import com.muskan.Hospital.Management.repository.InsuranceRepository;
import com.muskan.Hospital.Management.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance,Long patient_id){
        Patient patient = patientRepository.findById(patient_id)
                .orElseThrow(()-> new EntityNotFoundException("Patient not found with id : "+patient_id ));

        patient.setInsurance(insurance); // this line saves the insurance in database and attach the id here automatically

        insurance.setPatient((patient)); //bidirectionally consistency maintainence


        return patient;
    }
}
