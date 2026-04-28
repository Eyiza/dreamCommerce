package org.dreamcommerce.dreamcommerce.security.service;

import lombok.AllArgsConstructor;
import org.dreamcommerce.dreamcommerce.dto.response.UserResponse;

import org.dreamcommerce.dreamcommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DreamCommerceUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponse userResponse = userService.getUserBy(username);
        List<? extends GrantedAuthority> authorities = userResponse.getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new).toList();
        return new User(userResponse.getUsername(), userResponse.getPassword(), authorities);
    }
}
