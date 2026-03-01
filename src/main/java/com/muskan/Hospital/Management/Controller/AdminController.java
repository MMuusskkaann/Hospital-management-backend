package com.muskan.Hospital.Management.Controller;
import com.muskan.Hospital.Management.repository.PatientRepository;
import com.muskan.Hospital.Management.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final PatientService patientService;

    public AdminController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public ResponseEntity<?> getAllPatient(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ){
        return ResponseEntity.ok(patientService.getAllPatient(pageNumber, pageSize));
    }
}





