package com.muskan.Hospital.Management.Controller;

import com.muskan.Hospital.Management.dto.AppointmentResponseDto;
import com.muskan.Hospital.Management.entity.User;
import com.muskan.Hospital.Management.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final AppointmentService appointmentService;

    public DoctorController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentsOfDoctor() {

                User user  = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return ResponseEntity.ok(appointmentService.getAllAppointmentsOfDoctor(user.getId()));// info milage usse dpctor kei appointement kei jisne login kiya hua hei
    }
}
