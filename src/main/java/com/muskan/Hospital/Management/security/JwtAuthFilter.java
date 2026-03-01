package com.muskan.Hospital.Management.security;

import com.muskan.Hospital.Management.entity.User;
import com.muskan.Hospital.Management.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j  //simple login framework for java
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    //yei method innerly kaam krega req ko llega,response ko or puri kei puri filter chain ko bhi lega
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
           log.info("Incoming request : {}",request.getRequestURI());

           final String requestTokenHeader = request.getHeader("Authorization");

           if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")){
               filterChain.doFilter(request,response);
               return;
           }

           String token = requestTokenHeader.split("Bearer ")[1];
           //token like this ---> "Bearer uweehff87898h32ut723fevghvghv"

        String username = authUtil.getUsernameFromToken(token);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            User user = userRepository.findByUsername(username).orElseThrow();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request,response);
    }
}
