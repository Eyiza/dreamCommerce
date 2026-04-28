package org.dreamcommerce.dreamcommerce.controller;


import lombok.AllArgsConstructor;
import org.dreamcommerce.dreamcommerce.dto.request.RegisterUserRequest;
import org.dreamcommerce.dreamcommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest registerUserRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(registerUserRequest));
    }
}
