package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.LoginResponseDto;
import com.example.MyShroom_backend.dto.UserDto;
import com.example.MyShroom_backend.entity.Rank;
import com.example.MyShroom_backend.entity.UserEntity;
import org.hibernate.ObjectNotFoundException;

import java.util.List;

public interface UserService {
    public List<UserDto> findAll();

    void increaseRank(Long id, Rank rank);

    UserDto increaseStrikeCount(Long id);

    UserEntity findById(Long id) throws ObjectNotFoundException;
}
