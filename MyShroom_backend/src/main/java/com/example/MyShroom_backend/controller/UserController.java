package com.example.MyShroom_backend.controller;


import com.example.MyShroom_backend.AllowUser;
import com.example.MyShroom_backend.dto.LoginResponseDto;
import com.example.MyShroom_backend.dto.ReportPostDto;
import com.example.MyShroom_backend.dto.UserDto;
import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.repository.PostRepository;
import com.example.MyShroom_backend.security.JwtTokenService;
import com.example.MyShroom_backend.service.AuthService;
import com.example.MyShroom_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@PreAuthorize("hasRole('USER')")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    private final PostRepository postRepository;
    @GetMapping("/get-all")
    public ResponseEntity<List<UserDto>> getAll(){
        System.out.println("intra in get all");

        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/report")
    public ResponseEntity<?> reportPost(@RequestBody ReportPostDto dto ) {
        Optional<PostEntity> optionalPostEntity = this.postRepository.findById(dto.getPostId());
        if (optionalPostEntity.isPresent()) {

            PostEntity postEntity = optionalPostEntity.get();

            return ResponseEntity.ok(this.userService.report(dto.getUserId(), postEntity));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
