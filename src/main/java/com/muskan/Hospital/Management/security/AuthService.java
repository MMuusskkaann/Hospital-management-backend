package com.muskan.Hospital.Management.security;

import com.muskan.Hospital.Management.dto.LoginRequestDto;
import com.muskan.Hospital.Management.dto.LoginResponseDto;
import com.muskan.Hospital.Management.dto.SignupResponseDto;
import com.muskan.Hospital.Management.entity.User;
import com.muskan.Hospital.Management.entity.type.AuthProviderType;
import com.muskan.Hospital.Management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(@NotNull LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token,user.getId());
    }

    public SignupResponseDto signup(LoginRequestDto signupRequestDto) {
        User existingUser = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        if (existingUser != null) {
            throw new IllegalArgumentException("user already exists");
        }

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword())) // make sure password is hashed before saving!
                .build();

        user = userRepository.save(user);

        String token = authUtil.generateAccessToken(user);

        return new SignupResponseDto(user.getId(),user.getUsername());
    }

    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User auth2User, String registrationId) {

        // fetch provider type and provider id
        //save the provider type and provider id info with user
        // IF the user has an account : directly login
       // otherwise, first sign up and then login

        AuthProviderType providerType= authUtil.getProviderTypeFromRegistration(registrationId);
        String providerId =authUtil.determineProviderIdFromAuth2User(auth2User,registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId,providerType).orElse(null);

    }
}

