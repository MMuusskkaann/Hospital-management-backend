package com.muskan.Hospital.Management.repository;

import com.muskan.Hospital.Management.entity.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctors, Long> {
}
