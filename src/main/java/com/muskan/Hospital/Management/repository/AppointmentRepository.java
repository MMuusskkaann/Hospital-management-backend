package com.muskan.Hospital.Management.repository;

import com.muskan.Hospital.Management.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository  extends JpaRepository<Appointment,Long> {
}
