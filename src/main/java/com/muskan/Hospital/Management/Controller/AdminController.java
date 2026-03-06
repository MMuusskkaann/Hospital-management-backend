package com.muskan.Hospital.Management.Controller;
import com.muskan.Hospital.Management.dto.DoctorResponseDto;
import com.muskan.Hospital.Management.dto.OnboardDoctorRequestDto;
import com.muskan.Hospital.Management.service.DoctorService;
import com.muskan.Hospital.Management.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    public AdminController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/patients")
    public ResponseEntity<?> getAllPatient(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //it has a user data

        return ResponseEntity.ok(patientService.getAllPatient(pageNumber, pageSize));
    }

    @PostMapping("/onboardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onboardNewdoctor(@RequestBody OnboardDoctorRequestDto onBoardDoctorRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onBoardDoctorRequestDto));
    }
}





