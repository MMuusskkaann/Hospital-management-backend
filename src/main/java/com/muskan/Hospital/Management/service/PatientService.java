package com.muskan.Hospital.Management.service;

import com.muskan.Hospital.Management.dto.PatientResponseDto;
import com.muskan.Hospital.Management.entity.Patient;
import com.muskan.Hospital.Management.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private  final PatientRepository patientRepository;
//    private final ModelMapper modelMapper;

    @Transactional
    public Patient getPatientById(Long id){
     Patient p1 =  patientRepository.findById(id).orElseThrow();

     Patient p2 =  patientRepository.findById(id).orElseThrow();

     System.out.println(p1 == p2);

     p1.setName("ABC");
     return p1;
    }

//    public PatientRepository getAllPatient(Integer pageNumber, Integer pageSize) {
//
//        return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize))
//                .stream()
//                .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
//                .collect(Collectors.toList());
//    }
}
