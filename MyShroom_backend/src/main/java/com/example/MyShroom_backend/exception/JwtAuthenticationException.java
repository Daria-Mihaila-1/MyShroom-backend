package com.example.MyShroom_backend.exception;

public class JwtAuthenticationException extends Exception {
    public JwtAuthenticationException(Exception ex) {
        super(ex);
    }
}