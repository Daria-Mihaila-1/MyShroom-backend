package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.LoginResponseDto;
import com.example.MyShroom_backend.dto.UserDto;

import java.util.List;

public interface UserService {
    public List<UserDto> findAll();

    void increaseRank(Long id);

    void increaseStrikeCount(Long id);
}
