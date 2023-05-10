package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.UserDto;
import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.enums.Rank;
import com.example.MyShroom_backend.entity.UserEntity;
import com.example.MyShroom_backend.mapper.UserMapper;
import com.example.MyShroom_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public UserDto report(UserEntity reporterEntity, PostEntity postEntity) throws Exception {
        // Increase the strike count of the REPORTED user
        System.out.println(postEntity.getUser().getId());
        this.increaseStrikeCount(postEntity.getUser().getId());

            // Add a post to the list of reported posts for this REPORTER user
            System.out.println(reporterEntity.getId());
            List<PostEntity> existingReportedPosts = reporterEntity.getReportedPosts();

            // Check if reporter hasn't already reported this post
            if (!existingReportedPosts.contains(postEntity)) {
                System.out.println("existing reported posts heree *****************************" + existingReportedPosts.stream().map(PostEntity::getId).toList());

                reporterEntity.reportPost(postEntity);
                return this.userMapper.entityToDto(this.userRepository.save(reporterEntity));
            }
            else {
                System.out.println("e deja raprotat");
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
        if (optionalUserEntities.isPresent()){
            System.out.println("am gasit user care sa fi reportat aceste postari");
            List<UserEntity> userEntities = optionalUserEntities.get();
            System.out.println(userEntities.get(0).getReportedPosts());
            userEntities.forEach( userEntity -> {
                userEntity.getReportedPosts().remove(postEntity);
            });
            System.out.println(userEntities);
            userRepository.saveAll(userEntities);
        }
    }


}
