package com.example.MyShroom_backend.controller;
import com.example.MyShroom_backend.dto.RegisterResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import com.example.MyShroom_backend.dto.LoginRequestDto;
import com.example.MyShroom_backend.dto.LoginResponseDto;
import com.example.MyShroom_backend.dto.RegisterRequestDto;
import com.example.MyShroom_backend.enums.Rank;
import com.example.MyShroom_backend.entity.UserEntity;
import com.example.MyShroom_backend.repository.UserRepository;
import com.example.MyShroom_backend.security.JwtTokenService;
import com.example.MyShroom_backend.service.UserDetailsImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenService provider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = provider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto dto) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword());

        Optional<UserEntity> optionalUserEntity = userRepository.findByUserName((String) token.getPrincipal());
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            if (userEntity.getStrikes() > 3) {
                return new ResponseEntity<>(
                        "You have more than 3 reported posts...your account has been blocked",
                        HttpStatus.FORBIDDEN);
            }
            Authentication authentication = authenticationManager.authenticate(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtTokenService.generateJwtCookie(userDetails,userDetails.getId());
            String tokenString = jwtCookie.toString().split(";")[0].split("=")[1];
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new LoginResponseDto(tokenString));
        }
        return new ResponseEntity<>(
                "Account with username " + dto.getUserName() + " doesn't exists",
                HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser( @RequestBody RegisterRequestDto dto) {
        System.out.println("da sunt in register doamna");

        Optional<UserEntity> optionalUserEntity = userRepository.findByUserName(dto.getUserName());

        if (optionalUserEntity.isPresent()) {
             return new ResponseEntity<>(
                    "Account with username " + dto.getUserName() + " already exists",
                    HttpStatus.BAD_REQUEST);
        }
        // Create new user's account
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(dto.getUserName());
        userEntity.setPassword(encoder.encode(dto.getPassword()));
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setLastName(dto.getLastName());
        userEntity.setRank(Rank.BEGINNER);

        userRepository.save(userEntity);

        return ResponseEntity.ok(new RegisterResponseDto(userEntity.getUserName()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        System.out.println("da am dat logout doamna");
        ResponseCookie cookie = jwtTokenService.generateCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

}
