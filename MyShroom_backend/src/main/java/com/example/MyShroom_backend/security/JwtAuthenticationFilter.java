package com.example.MyShroom_backend.security;

import com.example.MyShroom_backend.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    AuthService authService;
    @Autowired
    private JwtTokenService jwtTokenService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String jwt = jwtTokenService.getJwtFromCookies(request);
            System.out.println("jtw ul:"+jwt);
            if (jwt != null && jwtTokenService.validateJwtToken(jwt)) {
                String username = jwtTokenService.getUserNameFromToken(jwt);
                jwtTokenService.getIdFromToken(jwt);
                System.out.println("Jhahaha");
                UserDetails userDetails = authService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("A mers");
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
            System.out.println("nu a mers");
        }


        filterChain.doFilter(request, response);
    }
}
