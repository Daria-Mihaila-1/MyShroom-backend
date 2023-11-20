package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.UserDto;
import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.enums.Rank;
import com.example.MyShroom_backend.entity.UserEntity;
import com.example.MyShroom_backend.mapper.UserMapper;
import com.example.MyShroom_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.ObjectNotFoundException;
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
        return userMapper.entitiesToDtos(userRepository.findAll());
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
    public UserDto report(UserEntity reporterEntity, PostEntity postEntity) throws Exception {
        // Increase the strike count of the REPORTED user
        this.increaseStrikeCount(postEntity.getUser().getId());

            // Add a post to the list of reported posts for this REPORTER user
            List<PostEntity> existingReportedPosts = reporterEntity.getReportedPosts();

            // Check if reporter hasn't already reported this post
            if (!existingReportedPosts.contains(postEntity)) {

                reporterEntity.reportPost(postEntity);
                return this.userMapper.entityToDto(this.userRepository.save(reporterEntity));
            }
            else {
                throw new Exception("reporter already reported this post");
            }
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

    @Override
    public void deleteReportedPost(PostEntity postEntity) {
        Optional <List<UserEntity>> optionalUserEntities = this.userRepository.findAllByReportedPostsContaining(postEntity);
        if (optionalUserEntities.isPresent() && !optionalUserEntities.isEmpty()){
            List<UserEntity> userEntities = optionalUserEntities.get();
            userEntities.forEach( userEntity -> {
                userEntity.getReportedPosts().remove(postEntity);
            });
            userRepository.saveAll(userEntities);
        }
    }
    @Override
    public UserDto deleteAccount(Long id) {
        Optional <UserEntity> optionalUserEntity = this.userRepository.findById(id);
        if(optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            this.userRepository.deleteById(id);
            return userMapper.entityToDto(userEntity);
        }
        return null;
    }

}
