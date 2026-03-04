package com.muskan.Hospital.Management.dto;

import com.muskan.Hospital.Management.entity.type.RoleType;
import lombok.Data;

import java.util.*;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class SignUpRequestDto {

    private String username;
    private String password;
    private String name;
    private Set<RoleType> roles = new HashSet<>();// signup  kei time prr role define nhi krte hwi

    public SignUpRequestDto(String username, String password, String name, Set<RoleType> roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }
}
