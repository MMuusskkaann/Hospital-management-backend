package com.muskan.Hospital.Management.entity;

import com.muskan.Hospital.Management.entity.type.AuthProviderType;
import com.muskan.Hospital.Management.entity.type.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "app_user",indexes =
        @Index(name = "idx_provider_id.provider_type",columnList = "providerId,providerType")
)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;


    private String providerId;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<RoleType> roles = new HashSet<>();

    // Implement all UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(); // return empty list or roles if you have any

        return (Collection<? extends GrantedAuthority>) roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()));  //ROLE_ lgana important hei
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
