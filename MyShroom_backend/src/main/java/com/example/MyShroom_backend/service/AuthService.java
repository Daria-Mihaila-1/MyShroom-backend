package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.MyShroom_backend.entity.UserEntity;

@Service
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUserName(username)
                .orElseThrow(() ->new UsernameNotFoundException("User with username " + username + " does not exist"));

        return UserDetailsImpl.build(userEntity);
    }

}
