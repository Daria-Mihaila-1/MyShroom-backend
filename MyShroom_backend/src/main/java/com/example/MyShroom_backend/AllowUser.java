package com.example.MyShroom_backend;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('USER')")
@Target(ElementType.METHOD)
public @interface AllowUser {
}
