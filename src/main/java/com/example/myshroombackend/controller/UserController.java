package com.example.myshroombackend.controller;

import com.example.myshroombackend.dto.UserDto;
import com.example.myshroombackend.entity.UserEntity;
import com.example.myshroombackend.mapper.UserMapper;
import com.example.myshroombackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLEngineResult;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/get-all")
    public ResponseEntity<List<UserDto>> getAll(){
        System.out.println("intra in get all");
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("add-user")
    public void addUser(@RequestBody UserDto dto) {
        userService.addUser(dto);
        System.out.println("added user");


    }
}
