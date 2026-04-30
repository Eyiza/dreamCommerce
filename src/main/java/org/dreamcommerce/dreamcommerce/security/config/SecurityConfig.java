package org.dreamcommerce.dreamcommerce.security.config;

import org.dreamcommerce.dreamcommerce.security.filter.DreamCommerceAuthenticationFilter;
import org.dreamcommerce.dreamcommerce.security.filter.DreamCommerceAuthorizationFilter;
import org.dreamcommerce.dreamcommerce.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

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
                                                   DreamCommerceAuthenticationFilter authenticationFilter,
                                                   DreamCommerceAuthorizationFilter authorizationFilter){
        // Allow access to endpoints that does not require security or tokens
        final String[] authWhiteList = new String[]{"/api/v1/login", "/api/v1/user"};
        return http
                .addFilterAt(authenticationFilter, BasicAuthenticationFilter.class) // Replaces the basic authentication filter
                .addFilterAfter(authorizationFilter, DreamCommerceAuthenticationFilter.class)
                .authorizeHttpRequests((r)->r.requestMatchers(POST, authWhiteList).permitAll())
                .authorizeHttpRequests((r)->r.requestMatchers(GET, "/api/v1/product").hasAnyAuthority("CUSTOMER", "TEST", "VENDOR", "ADMIN"))
                .authorizeHttpRequests((r)->r.requestMatchers(POST, "/api/v1/product", "/api/v1/product/**").hasAnyAuthority( "TEST", "VENDOR"))
                .authorizeHttpRequests((r)->r.requestMatchers("/admin", "/user/admin", "/admin/**").hasAnyAuthority("ADMIN"))
                .authorizeHttpRequests(r->r.anyRequest().authenticated()) // Allows access to other endpoints not specified above by any person as long as they have a valid authenticated token. It must be the last authorizeHttpRequests in this list as the hierarchy matters.
                .cors(Customizer.withDefaults()) // CORS config
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    // Using in memory details
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(new User("john@gmail.com", "john1234",
//                List.of(new SimpleGrantedAuthority("CUSTOMER"), new SimpleGrantedAuthority("ADMIN"))));
//        manager.createUser(new User("silas@gmail.com", "silas1234",
//                List.of(new SimpleGrantedAuthority("CUSTOMER"))));
//        return manager;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance(); // Plain-text
    }
}
