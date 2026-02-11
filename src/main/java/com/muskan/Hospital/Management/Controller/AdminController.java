package com.muskan.Hospital.Management.Controller;

import com.muskan.Hospital.Management.repository.PatientRepository;
import com.muskan.Hospital.Management.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientService patientService;


    @GetMapping("/patients")
    public ResponseEntity<PatientRepository> getAllPatient(
            @RequestParam(value = "page",defaultValue = "0") Integer PageNumber,
            @RequestParam(value = "size",defaultValue = "10") Integer pageSize
    ){
        return ResponseEntity.ok(patientService.getAllPatient(PageNumber,pageSize));
    }
}
