package com.example.MyShroom_backend.controller;


import com.example.MyShroom_backend.AllowUser;
import com.example.MyShroom_backend.dto.LoginResponseDto;
import com.example.MyShroom_backend.dto.UserDto;
import com.example.MyShroom_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PreAuthorize("hasRole('USER')")
@RestController
@RequiredArgsConstructor

@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/get-all")
    public ResponseEntity<List<UserDto>> getAll(){
        System.out.println("intra in get all");

        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/increase-rank")
    public ResponseEntity<?> increaseRank(@RequestParam Long id) {
        try{
            userService.increaseRank(id);
            return ResponseEntity.ok().build();
        }
        catch(IndexOutOfBoundsException e) {
            return new ResponseEntity<>(
                    "Rank is at its highest",
                    HttpStatus.BAD_REQUEST);
        }
    }

}
