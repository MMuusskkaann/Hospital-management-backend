package com.muskan.Hospital.Management.security;

import com.muskan.Hospital.Management.dto.LoginRequestDto;
import com.muskan.Hospital.Management.dto.LoginResponseDto;
import com.muskan.Hospital.Management.dto.SignUpRequestDto;
import com.muskan.Hospital.Management.dto.SignupResponseDto;
import com.muskan.Hospital.Management.entity.Patient;
import com.muskan.Hospital.Management.entity.User;
import com.muskan.Hospital.Management.entity.type.AuthProviderType;
import com.muskan.Hospital.Management.entity.type.RoleType;
import com.muskan.Hospital.Management.repository.PatientRepository;
import com.muskan.Hospital.Management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    public LoginResponseDto login(@NotNull LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token,user.getId());
    }

    public User signUpInternal(SignUpRequestDto signupRequestDto,AuthProviderType authProviderType,String providerId){
        User existingUser = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        if (existingUser != null) {
            throw new IllegalArgumentException("user already exists");
        }

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .roles(signupRequestDto.getRoles()) //Role.Patient
                .build();

        if(authProviderType == authProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        }

        userRepository.save(user);

        Patient patient = Patient.builder()
                .name(signupRequestDto.getName())
                .email(signupRequestDto.getUsername())
                .user(user)
                .build();
        patientRepository.save(patient);

        return user;
    }

    public SignupResponseDto signup(SignUpRequestDto signupRequestDto) {

        User user = signUpInternal(signupRequestDto,AuthProviderType.EMAIL,null);
        return new SignupResponseDto(user.getId(),user.getUsername());
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User auth2User, String registrationId) {

        // fetch provider type and provider id
        //save the provider type and provider id info with user
        // IF the user has an account : directly login
       // otherwise, first sign up and then login

        AuthProviderType providerType= authUtil.getProviderTypeFromRegistration(registrationId);
        String providerId =authUtil.determineProviderIdFromAuth2User(auth2User,registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId,providerType).orElse(null);
        String name = auth2User.getAttribute("name");
       String email = auth2User.getAttribute("email"); //user login through email

        //ak email id vala user ak hei jgh hona chahiye
        User emailUser = userRepository.findByUsername(email).orElse(null);

        if(user == null && emailUser == null){
            // sign up flow

            String username = authUtil.determinedUsUsernameFromOAuth2User(auth2User,registrationId,providerId);
            User signupResponseDto = signUpInternal(new SignUpRequestDto(username,null,name,Set.of(RoleType.PATIENT)),providerType,providerId);
        }

        else if (user != null){
            if(email != null && !email.isBlank()&& !email.equals(user.getUsername())){
                          user.setUsername(email);
                          userRepository.save(user);
            }
        }

        else{
            throw new BadCredentialsException("This email is already registered with provider " + emailUser.getProviderType());
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user),user.getId() );

        return ResponseEntity.ok(loginResponseDto);
    }
}

