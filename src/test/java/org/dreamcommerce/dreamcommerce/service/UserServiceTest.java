package org.dreamcommerce.dreamcommerce.service;

import org.dreamcommerce.dreamcommerce.dto.request.RegisterUserRequest;
import org.dreamcommerce.dreamcommerce.dto.response.RegisterUserResponse;
import org.dreamcommerce.dreamcommerce.model.User;
import org.dreamcommerce.dreamcommerce.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCanRegisterUser() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        User user = new User();

        when(modelMapper.map(registerUserRequest, User.class)).thenReturn(user);
        user.setId("123");
        user.setPassword("password");
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setId(user.getId());
        when(modelMapper.map(user, RegisterUserResponse.class)).thenReturn(registerUserResponse);

        RegisterUserResponse response = userService.register(registerUserRequest);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

}
