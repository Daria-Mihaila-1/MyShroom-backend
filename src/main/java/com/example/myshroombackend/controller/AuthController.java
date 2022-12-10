package com.example.myshroombackend.controller;

import com.example.myshroombackend.dto.LoginRequestDto;
import com.example.myshroombackend.dto.LoginResponseDto;
import com.example.myshroombackend.dto.RegisterRequestDto;
import com.example.myshroombackend.entity.Rank;
import com.example.myshroombackend.entity.UserEntity;
import com.example.myshroombackend.repository.UserRepository;
import com.example.myshroombackend.security.JwtTokenService;
import com.example.myshroombackend.service.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenService provider;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenService provider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.provider = provider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto dto) {
        UsernamePasswordAuthenticationToken token =  new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword());

        Optional <UserEntity> user = userRepository.findByUserName((String) token.getPrincipal());
        System.out.println(encoder.matches(token.getCredentials().toString(), user.get().getPassword()));

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = provider.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new LoginResponseDto(jwt));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser( @RequestBody RegisterRequestDto dto) {

        Optional<UserEntity> optionalUserEntity = userRepository.findByUserName(dto.getUserName());

        if (optionalUserEntity.isPresent()) {
            return ResponseEntity.badRequest().body("User already in database");
        }


        // Create new user's account
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(dto.getUserName());
        userEntity.setPassword(encoder.encode(dto.getPassword()));
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setLastName(dto.getLastName());
        userEntity.setRank(Rank.INTERMEDIATE);

        userRepository.save(userEntity);

        return ResponseEntity.ok("User registered successfully!");
    }

}

