package org.dreamcommerce.dreamcommerce.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.dreamcommerce.dreamcommerce.security.dto.requests.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@AllArgsConstructor
@Component // Add to the context
public class DreamCommerceAuthenticationFilter extends OncePerRequestFilter {
    // Logic of the filter is implemented here if using "implements Filter"
    // "implements Filter" is not recommended because it is not predictable as It can be called moe than once for one request.
    // Another type we can use is "extends OncePerRequestFilter", "extends UsernamePasswordAuthenticationFilter"
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//    }

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().equals("/api/v1/login") || !request.getMethod().equals(POST.name())) {
            throw new RuntimeException("Authentication failed");
        }
        // 1. Read JSON data from request body
        InputStream inputStream = request.getInputStream(); // json - {"username": "", "password": ""}
        // 2. Convert json to Java object
        LoginRequest loginRequest = objectMapper.readValue(inputStream, LoginRequest.class);
        // 3. Send auth credentials to the Authentication Manager
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticationResult = authenticationManager.authenticate(authentication);
        String[] authorities = authenticationResult.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        Algorithm algorithm = Algorithm.HMAC256(Base64.getEncoder()
                .encodeToString("this is our very very secure secret".getBytes()));
        String token = JWT.create()
                .withSubject(username)
                .withIssuer("dreamCommerce")
                .withClaim("username", username) // Repeat for every data you want in the token like id
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withArrayClaim("roles", authorities)
                .sign(algorithm);

        Map<String, String> loginResponse = new HashMap<>();
        loginResponse.put("access_token", token);
        loginResponse.put("token_type", "Bearer");

        response.getOutputStream().write(objectMapper.writeValueAsBytes(loginResponse));
        response.setContentType(APPLICATION_JSON_VALUE);
        response.flushBuffer(); // pushes the data to the response

        // Calls the next filter in the chain
        filterChain.doFilter(request, response);
    }


}
