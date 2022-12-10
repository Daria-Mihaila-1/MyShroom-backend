package com.example.myshroombackend.service;

import com.example.myshroombackend.dto.UserDto;
import com.example.myshroombackend.entity.UserEntity;
import com.example.myshroombackend.mapper.UserMapper;
import com.example.myshroombackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.beans.Encoder;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAll() {
        List<UserDto> userDtos = userMapper.entitiesToDtos(userRepository.findAll());
        return userDtos;
    }

    @Override
    public void addUser(UserDto dto) {
        UserEntity entity = userMapper.dtoToEntity(dto);
        userRepository.save(entity);
    }
}
