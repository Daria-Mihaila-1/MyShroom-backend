package com.example.myshroombackend.exception;

import io.jsonwebtoken.JwtException;

public class JwtAuthenticationException extends Exception {
    public JwtAuthenticationException(Exception ex) {
    super(ex);
    }
}
