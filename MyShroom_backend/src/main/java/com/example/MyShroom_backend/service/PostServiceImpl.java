package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.*;
import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.enums.MushroomType;
import com.example.MyShroom_backend.enums.Rank;
import com.example.MyShroom_backend.entity.UserEntity;
import com.example.MyShroom_backend.mapper.PostMapper;
import com.example.MyShroom_backend.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements  PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final ClassifierService classifierService;
    private final UserService userService;

    @Override
    public List<PostDto> findAll() {
        List<PostEntity> entities = postRepository.findAll();
        return postMapper.entitiesToDtos(entities);
    }

    @Override
    public Optional<PostEntity> findById(Long id) {
        return this.postRepository.findById(id);
    }
//TODO: scale img to fit a square properly
    @Override
    public PostDto addPost(UploadPostDto newDTo) {
        try {
            PostEntity newEntity = postMapper.uploadDtoToEntity(newDTo);

            // Increase Rank of user if necessary
            UserEntity userOfPost = this.userService.findById(newDTo.getUserId());
            if (this.postRepository.findAllByUserId(userOfPost.getId()).size() == 5 && userOfPost.getRank() == Rank.BEGINNER) {
                this.userService.increaseRank(userOfPost.getId(), Rank.INTERMEDIATE);
            } else if (this.postRepository.findAllByUserId(userOfPost.getId()).size() == 15 && userOfPost.getRank() == Rank.INTERMEDIATE) {
                this.userService.increaseRank(userOfPost.getId(), Rank.EXPERT);
            }

            // Set the highest predicted score label as the predicted_mushroom_type of the post for further improvements
            Map<String, Double> scores = this.classifierService.predict(new ClassifierRequestDto(newDTo.getBase64Img()));

            Map.Entry<String, Double> firstScore = scores.entrySet().iterator().next();
            if (firstScore.getValue() > 65) {
                MushroomType mushroomType = MushroomType.valueOf(firstScore.getKey().toUpperCase());
                newEntity.setPredicted_mushroom_type(mushroomType);
            }

            PostEntity newPostEntity = postRepository.save(newEntity);

            return (postMapper.entityToDto(newPostEntity));
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PostDto deletePost(Long id) throws EntityNotFoundException {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
        if (optionalPostEntity.isPresent()) {
            PostEntity postEntity = optionalPostEntity.get();
            this.userService.deleteReportedPost(postEntity);
            postRepository.deleteById(id);
            return postMapper.entityToDto(postEntity);
        } else {
            throw new EntityNotFoundException("Entity not found with id " + id);
        }

    }

    @Override
    public PostDto updatePost(UpdatePostDto newPostDto) throws EntityNotFoundException {

        Optional<PostEntity> optionalPostEntity = postRepository.findById(newPostDto.getId());
        if (optionalPostEntity.isPresent()) {

            PostEntity postEntity = this.postMapper.updateDtoToEntity(newPostDto);
            // Set the highest predicted score label as the predicted_mushroom_type of the post for further improvements
            Map<String, Double> scores = this.classifierService.predict(new ClassifierRequestDto(this.postMapper.entityToDto(postEntity).getBase64Img()));

            Map.Entry<String, Double> firstScore = scores.entrySet().iterator().next();
            if (firstScore.getValue() > 65) {
                MushroomType mushroomType = MushroomType.valueOf(firstScore.getKey().toUpperCase());
                postEntity.setPredicted_mushroom_type(mushroomType);
            }

            postRepository.save(postEntity);

            PostEntity updatedPostEntity = postRepository.findById(postEntity.getId()).get();
            return postMapper.entityToDto(updatedPostEntity);
        } else {
            throw new EntityNotFoundException("Entity not found with id " + newPostDto.getId());
        }
    }



    @Override
    public List<PostDto> getMyPosts(Long id) {
        List<PostEntity> myPosts = postRepository.findAllByUserId(id);
        return postMapper.entitiesToDtos(myPosts);
    }


    @Override
    public List<PostDto> getPostsNotReportedBy(Long id) {
        // find the UserEntity who is reporting right now
        UserEntity userEntity = this.userService.findById(id);
        if (userEntity.getReportedPosts().size() != 0) {
            // if he has reported some ==> return all posts that don't have their Id in the list of reported posts of the current reported user
            return this.postMapper.entitiesToDtos(this.postRepository.findAllByIdNotIn(
                    userEntity.getReportedPosts()
                            .stream()
                            .map(PostEntity::getId)
                            .toList()));
        }
        //if the userEntity didn't report anything until now return all posts
        else return this.postMapper.entitiesToDtos(this.postRepository.findAll());
    }

    @Override
    public List<PostDto> getPostsReportedBy(Long id) {
        UserEntity userEntity = this.userService.findById(id);
        if (userEntity.getReportedPosts().size() != 0) {
            return this.postMapper.entitiesToDtos(userEntity.getReportedPosts());
        }
        return null;
    }
}
