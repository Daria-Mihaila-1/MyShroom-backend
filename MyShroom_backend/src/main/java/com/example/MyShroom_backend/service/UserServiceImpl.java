package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.LoginResponseDto;
import com.example.MyShroom_backend.dto.UserDto;
import com.example.MyShroom_backend.entity.Rank;
import com.example.MyShroom_backend.entity.UserEntity;
import com.example.MyShroom_backend.mapper.UserMapper;
import com.example.MyShroom_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public void increaseRank(Long id) throws ArrayIndexOutOfBoundsException{

        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()){
            UserEntity userEntity = optionalUserEntity.get();
            int index = userEntity.getRank().ordinal();

                index++;
                userEntity.setRank(Rank.values()[index]);
                userRepository.save(userEntity);


        }
    }

    @Override
    public void increaseStrikeCount(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()){
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setStrikes(userEntity.getStrikes() + 1);
            userRepository.save(userEntity);
        }

    }


}
