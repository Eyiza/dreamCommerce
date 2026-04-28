package org.dreamcommerce.dreamcommerce.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreamcommerce.dreamcommerce.security.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.POST;

@Component
@AllArgsConstructor
@Slf4j
public class DreamCommerceAuthorizationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        boolean isRequestToAuthEndpoint = request.getMethod().equals(POST.name()) &&
                request.getServletPath().equals("/api/v1/login");

        if (isRequestToAuthEndpoint) {
            filterChain.doFilter(request, response);
            return;
        } else {
            String authHeader = request.getHeader(AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String accessToken = authHeader.substring("Bearer ".length());
                UserDetails userDetails = jwtService.validate(accessToken);
                // Place in context
                SecurityContextHolder.getContext().setAuthentication(
                        // Like an hashmap
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
                                userDetails.getAuthorities()));
                // log.info("authorities::: {}",SecurityContextHolder.getContext().getAuthentication());
            }
            filterChain.doFilter(request, response);
        }
    }
}
