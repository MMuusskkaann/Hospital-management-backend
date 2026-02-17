package com.muskan.Hospital.Management.repository;

//import com.muskan.Hospital.Management.model.AuthProviderType;
import com.muskan.Hospital.Management.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

//    Optional<User> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
}