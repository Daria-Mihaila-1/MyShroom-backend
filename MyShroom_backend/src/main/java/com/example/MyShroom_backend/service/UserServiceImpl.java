package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.LoginResponseDto;
import com.example.MyShroom_backend.dto.UserDto;
import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.entity.Rank;
import com.example.MyShroom_backend.entity.UserEntity;
import com.example.MyShroom_backend.mapper.UserMapper;
import com.example.MyShroom_backend.repository.PostRepository;
import com.example.MyShroom_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public void increaseRank(Long id, Rank rank) throws ArrayIndexOutOfBoundsException{

        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()){
            UserEntity userEntity = optionalUserEntity.get();
                userEntity.setRank(rank);
                userRepository.save(userEntity);
        }
    }

    @Override
    public UserDto increaseStrikeCount(Long id) {

            Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
            if (optionalUserEntity.isPresent()) {
                UserEntity userEntity = optionalUserEntity.get();
                userEntity.setStrikes(userEntity.getStrikes() + 1);

                userRepository.save(userEntity);
                return this.userMapper.entityToDto(userEntity);
            }
        return null;
    }

    @Override
    public UserDto report(Long reporterId, PostEntity postEntity) {
        // Increase the strike count of the REPORTED user
        this.increaseStrikeCount(postEntity.getUser().getId());

        // Add a post to the list of reported posts for this REPORTER user
        Optional<UserEntity> reporter = this.userRepository.findById(reporterId);
        if (reporter.isPresent()) {
            UserEntity reporterEntity = reporter.get();
            Set<PostEntity> newReportedPosts = reporterEntity.getReported_posts();
            newReportedPosts.add(postEntity);
            reporterEntity.setReported_posts(newReportedPosts);
            return this.userMapper.entityToDto(this.userRepository.save(reporterEntity));
        }
        throw new ObjectNotFoundException( new Object(), "reporter was not found my dude");
    }


    @Override
    public UserEntity findById(Long id) throws ObjectNotFoundException {
        Optional <UserEntity> optionalUserEntity = this.userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            return userEntity;
        }
        else {
            throw new RuntimeException("User with given Id does not exist");
        }
    }


}
