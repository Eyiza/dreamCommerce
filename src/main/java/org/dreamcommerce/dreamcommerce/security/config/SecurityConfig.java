package org.dreamcommerce.dreamcommerce.security.config;

import org.dreamcommerce.dreamcommerce.security.filter.DreamCommerceAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        return http
//                .authorizeHttpRequests((r)->r.anyRequest()
//                        .hasAnyRole("TEST"))
//                .csrf(AbstractHttpConfigurer::disable)
//                .build();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   DreamCommerceAuthenticationFilter authenticationFilter){
        final String[] authWhiteList = new String[]{"TEST", "CUSTOMER", "ADMIN"};
        return http
                .addFilterAt(authenticationFilter, BasicAuthenticationFilter.class) // Replaces the basic authentication filter
                .authorizeHttpRequests((r)->r.anyRequest()
                        .hasAnyRole(authWhiteList))
                        .csrf(AbstractHttpConfigurer::disable)
                        .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(new User("john@gmail.com", "john1234",
                List.of(new SimpleGrantedAuthority("CUSTOMER"), new SimpleGrantedAuthority("ADMIN"))));
        manager.createUser(new User("silas@gmail.com", "silas1234",
                List.of(new SimpleGrantedAuthority("CUSTOMER"))));
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }
}
