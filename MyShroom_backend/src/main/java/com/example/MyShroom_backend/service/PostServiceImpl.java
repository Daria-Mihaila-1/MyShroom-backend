package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.PostDto;
import com.example.MyShroom_backend.dto.UploadPostDto;
import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.mapper.PostMapper;
import com.example.MyShroom_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements  PostService{
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    @Override
    public List<PostDto> findAll() {
        List<PostEntity> entities = postRepository.findAll();
        return postMapper.entitiesToDtos(entities);
    }

    @Override
    public PostDto addPost(UploadPostDto newDTo) {
        PostEntity newEntity = postMapper.uploadDtoToEntity(newDTo);
        return(postMapper.entityToDto(postRepository.save(newEntity)));
        //TODO: add check the photo with the classifier and add it to one of the tables
    }
}
