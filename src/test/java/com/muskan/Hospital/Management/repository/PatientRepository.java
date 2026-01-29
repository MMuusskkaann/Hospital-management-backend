package com.muskan.Hospital.Management.repository;


import com.muskan.Hospital.Management.entity.Patient;
import org.hibernate.boot.model.source.spi.JpaCallbackSource;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
