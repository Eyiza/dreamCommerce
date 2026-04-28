package org.dreamcommerce.dreamcommerce.service;

import lombok.AllArgsConstructor;
import org.dreamcommerce.dreamcommerce.dto.request.RegisterUserRequest;
import org.dreamcommerce.dreamcommerce.dto.response.RegisterUserResponse;
import org.dreamcommerce.dreamcommerce.dto.response.UserResponse;
import org.dreamcommerce.dreamcommerce.exception.ResourceNotFoundException;
import org.dreamcommerce.dreamcommerce.model.User;
import org.dreamcommerce.dreamcommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        User user = modelMapper.map(registerUserRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(List.of("CUSTOMER"));
        return modelMapper.map(userRepository.save(user), RegisterUserResponse.class);
    }

    @Override
    public UserResponse getUserBy(String username) {
       User user = userRepository.findByUsername(username)
               .orElseThrow(()-> new ResourceNotFoundException("User not found"));
       return modelMapper.map(user, UserResponse.class);
    }
}
