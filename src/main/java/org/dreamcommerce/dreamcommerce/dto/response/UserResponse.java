package org.dreamcommerce.dreamcommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private String username;
    private String password;
    private List<String> authorities;
}
