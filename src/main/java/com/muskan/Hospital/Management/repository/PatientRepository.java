package com.muskan.Hospital.Management.repository;
import org.springframework.data.domain.Pageable;
import com.muskan.Hospital.Management.dto.BloodGroupcountResponseEntity;
import com.muskan.Hospital.Management.entity.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

import java.time.LocalDate;

@Repository
public interface PatientRepository  extends JpaRepository<Patient,Long> {
    Patient findByName(String name);
    List<Patient> findByBirthDateOrEmail(LocalDate birthDate,String email);

    List<Patient> findByBirthDateBetween(LocalDate startDate,LocalDate endDate);

    List<Patient> findByNameContaining(String query);

//    @Query("SELECT p FROM Patient p where p.Gender = ?1")
//    List<Patient> findByGender(@Param("Gender") String Gender);

    @Query("select p from Patient p where p.birthDate > :birthDate")
    List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate);

//    @Query("SELECT new com.muskan.Hospital.Management.dto.BloodGroupcountResponseEntity(p.bloodGroup, COUNT(p)) " +
//            "FROM Patient p " +
//            "GROUP BY p.bloodGroup")
//    List<BloodGroupcountResponseEntity> countEachBloodGroupType();

    @Query("select new com.muskan.Hospital.Management.dto.BloodGroupcountResponseEntity(p.bloodGroup, count(p)) from Patient p group by p.bloodGroup")
    List<BloodGroupcountResponseEntity> countEachBloodGroupType();

    @Query(value = "select * from patient_table", nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Patient p SET p.name = :name where p.id = :id")
    int updateNameWithId(@Param("name") String name, @Param("id") Long id);

}
