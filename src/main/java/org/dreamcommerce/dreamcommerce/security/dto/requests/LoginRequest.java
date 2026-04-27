package org.dreamcommerce.dreamcommerce.security.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
