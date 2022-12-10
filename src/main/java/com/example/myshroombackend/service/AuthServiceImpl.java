package com.example.myshroombackend.service;


import com.example.myshroombackend.entity.UserEntity;
import com.example.myshroombackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUserName(username)
                .orElseThrow(() ->new UsernameNotFoundException("User with username " + username + " does not exist"));

        System.out.println("din userDetailsService" + userEntity.getUserName() + " " + userEntity.getPassword());
          return UserDetailsImpl.build(userEntity);
    }

}

