package com.example.myshroombackend.service;

import com.example.myshroombackend.dto.UserDto;

import java.util.List;

public interface UserService {
    public List<UserDto> findAll();

    void addUser(UserDto dto);
}
