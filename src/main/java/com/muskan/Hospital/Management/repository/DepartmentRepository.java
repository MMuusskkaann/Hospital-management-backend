package com.muskan.Hospital.Management.repository;

import com.muskan.Hospital.Management.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
