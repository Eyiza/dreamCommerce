package org.dreamcommerce.dreamcommerce.service;

import org.dreamcommerce.dreamcommerce.dto.request.RegisterUserRequest;
import org.dreamcommerce.dreamcommerce.dto.response.RegisterUserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.dreamcommerce.dreamcommerce.dto.response.UserResponse;

public interface UserService {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    UserResponse getUserBy(String username);
}
