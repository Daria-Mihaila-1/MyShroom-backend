package com.example.MyShroom_backend.controller;


import com.example.MyShroom_backend.AllowUser;
import com.example.MyShroom_backend.dto.LoginResponseDto;
import com.example.MyShroom_backend.dto.UserDto;
import com.example.MyShroom_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasRole('USER')")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/get-all")
    public ResponseEntity<List<UserDto>> getAll(){
        System.out.println("intra in get all");

        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("increase-strike-count/{id}")
    public ResponseEntity<UserDto> getStriked(@PathVariable Long id) {
        System.out.println("I have been struck");
        return  ResponseEntity.ok(userService.increaseStrikeCount(id));
    }
}
