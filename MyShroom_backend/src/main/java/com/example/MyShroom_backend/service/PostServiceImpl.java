package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.PostDto;
import com.example.MyShroom_backend.dto.UploadPostDto;
import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.mapper.PostMapper;
import com.example.MyShroom_backend.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

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

    @Override
    public PostDto deletePost(Long id) throws EntityNotFoundException {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
        if (optionalPostEntity.isPresent()){
            PostEntity postEntity = optionalPostEntity.get();
            postRepository.deleteById(id);
            return postMapper.entityToDto(postEntity);
        }
        else {
            throw new EntityNotFoundException("Entity not found with id " + id);
        }

    }

    @Override
    public PostDto updatePost( PostDto newPostDto) throws EntityNotFoundException{

        Optional<PostEntity> optionalPostEntity = postRepository.findById(newPostDto.getId());
        if (optionalPostEntity.isPresent()){

            PostEntity postEntity = optionalPostEntity.get();
            postEntity.setTitle(newPostDto.getTitle());
            postEntity.setMushroomType(newPostDto.getMushroomType());
            postEntity.setLatitude(newPostDto.getLatitude());
            postEntity.setLongitude(newPostDto.getLongitude());
            postEntity.setDescription(newPostDto.getDescription());
            String imgEncoded = newPostDto.getBase64Img();
            if ( imgEncoded != null ) {
                byte[] decodedImgBytes = Base64.getDecoder().decode(imgEncoded);
                postEntity.setImg(decodedImgBytes);
            }
            postRepository.save(postEntity);
            PostEntity updatedPostEntity = postRepository.findById(postEntity.getId()).get();
            return postMapper.entityToDto(updatedPostEntity);
        }
        else {
            throw new EntityNotFoundException("Entity not found with id " + newPostDto.getId());
        }
    }
}
