package org.dreamcommerce.dreamcommerce.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateAccessToken(Authentication authentication);

    UserDetails validate(String accessToken);
}
