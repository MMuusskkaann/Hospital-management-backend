package com.muskan.Hospital.Management.security;

import com.muskan.Hospital.Management.entity.User;
import com.muskan.Hospital.Management.entity.type.AuthProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static io.jsonwebtoken.Jwts.*;

@Component
@Slf4j
public class AuthUtil {
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;  //secret key

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8)); //token banane kei liye use hoga
    }

    public String generateAccessToken(User user){
        return builder()
                ///body part
                .subject(user.getUsername())
                .claim("userId",user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() +1000*60*10))
                .signWith(getSecretKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims =  Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public AuthProviderType getProviderTypeFromRegistration(String registrationId){
        return switch (registrationId.toLowerCase()){
            case "google" -> AuthProviderType.GOOGLE;
            case "github" -> AuthProviderType.GITHUB;
            case "facebook" -> AuthProviderType.FACEBOOK;
            default -> throw new IllegalArgumentException("Unsupported OAuth2 provider : " + registrationId);

        };
    }

    public String determineProviderIdFromAuth2User(OAuth2User oAuth2User,String registrationId){
        String providerId = switch (registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id");
            default -> {
                log.error("Unsupported OAuth2 provider : {}",registrationId);
                throw new IllegalArgumentException("Unsupported OAuth provider : " + registrationId);
            }
        };

        if(providerId == null || providerId.isBlank()){
            log.error("Unable to determine providerId for provider: {}", registrationId);
            throw new IllegalArgumentException("Unable to determine provideId for OAuth2 login");
        }
        return providerId;
    }
}
