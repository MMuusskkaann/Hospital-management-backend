package com.muskan.Hospital.Management.entity;

import com.muskan.Hospital.Management.model.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "app_user", indexes = {
        @Index(name = "idx_provider_id_provider_type", columnList = "providerId, providerType")
})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(unique = true, nullable = false)
    private String username;
    private String password;

    private String providerId;

//    @Enumerated(EnumType.STRING)
//    private AuthProviderType providerType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles = new HashSet<>();


    public Set<SimpleGrantedAuthority> getAuthorities() {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
//                .collect(Collectors.toSet());
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        roles.forEach(
//                role -> {
//                    Set<SimpleGrantedAuthority> permissions = RolePermissionMapping.getAuthoritiesForRole(role);
//                    authorities.addAll(permissions);
//                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
//                }
//        );
        return authorities;
    }
}