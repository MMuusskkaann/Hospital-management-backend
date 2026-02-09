package com.muskan.Hospital.Management;

import com.muskan.Hospital.Management.entity.Insurance;
import com.muskan.Hospital.Management.entity.Patient;
import com.muskan.Hospital.Management.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class Insurancetest {

    @Autowired
    private InsuranceService insuranceService;
    @Test
    public void testInsurance(){
        Insurance insurance= Insurance.builder()
                .policyNumber("HDFC_1234")
                .provider("HDFC")
                .validUntil(LocalDate.of(2030, 12 ,12 ))
                .build();

       Patient patient =  insuranceService.assignInsuranceToPatient(insurance, 1L);

       System.out.println(patient);
    }
}
