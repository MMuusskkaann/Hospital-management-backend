package com.muskan.Hospital.Management.dto;

import com.muskan.Hospital.Management.dto.BloodGroupType;

public class BloodGroupcountResponseEntity {

    private BloodGroupType bloodGroupType;
    private Long count;

    // Explicit constructor for JPA
    public BloodGroupcountResponseEntity(BloodGroupType bloodGroupType, Long count) {
        this.bloodGroupType = bloodGroupType;
        this.count = count;
    }

    // Getters and setters
    public BloodGroupType getBloodGroupType() {
        return bloodGroupType;
    }

    public void setBloodGroupType(BloodGroupType bloodGroupType) {
        this.bloodGroupType = bloodGroupType;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
