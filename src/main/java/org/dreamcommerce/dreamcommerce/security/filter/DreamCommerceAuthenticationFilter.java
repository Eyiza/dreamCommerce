package org.dreamcommerce.dreamcommerce.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Not recommended because it is not predictable as It can be called moe than once for one request.
// Another type we can use is "extends OncePerRequestFilter", "extends UsernamePasswordAuthenticationFilter"

public class DreamCommerceAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
    // Logic of the filter is implemented here if using "implements Filter"
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//    }

}
