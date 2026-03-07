package com.muskan.Hospital.Management.security;
import com.muskan.Hospital.Management.entity.type.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import static com.muskan.Hospital.Management.entity.type.PermissionType.*;
import static com.muskan.Hospital.Management.model.RoleType.*;  // * -> all role accessable


@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
public class WebSecurityConfig {

    private  final JwtAuthFilter jwtAuthFilter;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**","/auth/**").permitAll()
                        .requestMatchers(httpMethod.DELETE,"/admin").hasAuthority(APPOINTMENT_DELETE.name()) //agr yei authority hei toh hei delete call krne denge
                        .requestMatchers("/admin/**").hasRole(RoleType.ADMIN.name()) //admin vale route access by an admin
                        .requestMatchers("/doctors/**").hasAnyRole(RoleType.DOCTOR.name(),ADMIN.name())
                        .anyRequest().authenticated()
                )


                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth2 -> oAuth2
                                .failureHandler(
                        (request, response, exception) -> {
                            log.error("OAuth2 Error : {}", exception.getMessage());

                            handlerExceptionResolver.resolveException(request, response, null, exception);
                        }
                )
                        .successHandler(oAuth2SuccessHandler)
                )
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.accessDeniedHandler((request, response, accessDeniedException) -> {
                            handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
                        }));

        return http.build();
    }
}
