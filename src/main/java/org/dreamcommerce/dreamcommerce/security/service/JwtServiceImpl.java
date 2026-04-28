package org.dreamcommerce.dreamcommerce.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String generateAccessToken(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        String[] authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        Algorithm algorithm = Algorithm.HMAC256(Base64.getEncoder()
                .encodeToString("this is our very very secure secret".getBytes()));
        return JWT.create()
                .withSubject(username)
                .withIssuer("dreamCommerce")
                .withClaim("username", username) // Repeat for every data you want in the token like id
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withArrayClaim("roles", authorities)
                .sign(algorithm);
    }
}
