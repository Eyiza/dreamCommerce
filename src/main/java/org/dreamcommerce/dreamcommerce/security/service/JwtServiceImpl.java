package org.dreamcommerce.dreamcommerce.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.signing.key}")
    private String signingKey;

    private final UserDetailsService userDetailsService;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(Base64.getEncoder()
                .encodeToString(signingKey.getBytes()));
    }

    @Override
    public String generateAccessToken(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        String[] authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        Algorithm algorithm = getAlgorithm();
        return JWT.create()
                .withSubject(username)
                .withIssuer("dreamCommerce")
                .withClaim("username", username) // Repeat for every data you want in the token like id
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withArrayClaim("roles", authorities)
                .sign(algorithm);
    }

    @Override
    public UserDetails validate(String accessToken) {
        Algorithm algorithm = getAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("dreamCommerce")
                .withClaimPresence("username")
                .withClaimPresence("roles")
                .build();

        DecodedJWT decodedJWT = verifier.verify(accessToken);
        String username = decodedJWT.getClaim("username").asString();
        // log.info("username ---> {}", username);
        // log.info("userDetails ---> {}", userDetails);
        return userDetailsService.loadUserByUsername(username);
    }
}
