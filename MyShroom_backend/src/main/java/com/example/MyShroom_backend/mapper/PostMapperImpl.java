package com.example.MyShroom_backend.mapper;

import com.example.MyShroom_backend.dto.*;
import com.example.MyShroom_backend.entity.DocumentEntity;
import com.example.MyShroom_backend.entity.PostEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.example.MyShroom_backend.entity.Type;
import com.example.MyShroom_backend.entity.UserEntity;
import com.example.MyShroom_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class PostMapperImpl implements PostMapper {

    private final UserRepository userRepository;
    private final DocumentMapper documentMapper;

    @Override
    public PostDto entityToDto(PostEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String mushroomType = null;
        double latitude = 0.0f;
        double longitude = 0.0f;
        String description = null;
        String img = null;
        String date = null;
        String time = null;
        List<DocumentDto> attachments = null;
        Long userId = null;
        Type type = null;

        id = entity.getId();
        title = entity.getTitle();
        mushroomType = entity.getMushroomType();
        latitude = entity.getLatitude();
        longitude = entity.getLongitude();
        description = entity.getDescription();
        byte[] img1 = entity.getImg();
        if ( img1 != null ) {
            img = Base64.getEncoder().encodeToString(img1);
        }
        date = entity.getDate().toString();
        time = entity.getTime().toString();
        attachments = documentMapper.entitiesToDtos(entity.getAttachments());
        userId = entity.getUser().getId();
        type = entity.getType();

        return new PostDto( id, title, mushroomType, latitude, longitude, description, img, date, time,attachments, type, userId);
    }

    @Override
    public PostEntity dtoToEntity(PostDto dto) {
        if ( dto == null ) {
            return null;
        }

        PostEntity postEntity = new PostEntity();

        postEntity.setId( dto.getId() );
        postEntity.setTitle( dto.getTitle() );
        postEntity.setMushroomType( dto.getMushroomType() );
        postEntity.setLatitude( dto.getLatitude() );
        postEntity.setLongitude( dto.getLongitude() );
        postEntity.setDescription( dto.getDescription() );
        String imgEncoded = dto.getBase64Img();
        if ( imgEncoded != null ) {
            byte[] decodedImgBytes = Base64.getDecoder().decode(imgEncoded);
            postEntity.setImg(decodedImgBytes);
        }
        postEntity.setDate(LocalDate.parse(dto.getDate()));
        postEntity.setTime(LocalTime.parse(dto.getTime()));
        List<DocumentEntity> attachments = documentMapper.dtosToEntities(dto.getAttachments());
        postEntity.setAttachments(attachments);
        postEntity.setType(dto.getType());


        Optional<UserEntity> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            postEntity.setUser(user);
        }
        else{
            postEntity.setUser(null);
        }

        return postEntity;
    }

    @Override
    public PostEntity uploadDtoToEntity(UploadPostDto dto) {
        if ( dto == null ) {
            return null;
        }

        PostEntity postEntity = new PostEntity();

        postEntity.setTitle( dto.getTitle() );
        postEntity.setMushroomType( dto.getMushroomType() );
        postEntity.setLatitude((float) dto.getLatitude());
        postEntity.setLongitude((float) dto.getLongitude());
        postEntity.setDescription( dto.getDescription() );
        String imgEncoded = dto.getBase64Img();
        if ( imgEncoded != null ) {
            byte[] decodedImgBytes = Base64.getDecoder().decode(imgEncoded);
            System.out.println(decodedImgBytes);
            postEntity.setImg(decodedImgBytes);
        }
        postEntity.setDate(LocalDate.now());
        postEntity.setTime(LocalTime.now());

        List<DocumentEntity> attachments = documentMapper.dtosToEntities(dto.getAttachments());
        postEntity.setAttachments(attachments);
        postEntity.setType(dto.getType());
        Optional<UserEntity> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            postEntity.setUser(user);
        }
        else{
            postEntity.setUser(null);
        }


        return postEntity;
    }

    @Override
    public List<PostEntity> dtosToEntities(List<PostDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<PostEntity> list = new ArrayList<PostEntity>( dtos.size() );
        for ( PostDto postDto : dtos ) {
            list.add( dtoToEntity( postDto ) );
        }

        return list;
    }

    @Override
    public List<PostDto> entitiesToDtos(List<PostEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PostDto> list = new ArrayList<PostDto>( entities.size() );
        for ( PostEntity postEntity : entities ) {
            list.add( entityToDto( postEntity ) );
        }

        return list;
    }

    @Override
    public PostEntity updateDtoToEntity(UpdatePostDto dto) {
        if ( dto == null ) {
            return null;
        }
        PostEntity postEntity = new PostEntity();
        postEntity.setId(dto.getId());
        postEntity.setTitle( dto.getTitle() );
        postEntity.setMushroomType( dto.getMushroomType() );
        postEntity.setLatitude((float) dto.getLatitude());
        postEntity.setLongitude((float) dto.getLongitude());
        postEntity.setDescription( dto.getDescription() );
        String imgEncoded = dto.getBase64Img();
        if ( imgEncoded != null ) {
            byte[] decodedImgBytes = Base64.getDecoder().decode(imgEncoded);
            System.out.println(decodedImgBytes);
            postEntity.setImg(decodedImgBytes);
        }
        postEntity.setDate(LocalDate.now());
        postEntity.setTime(LocalTime.now());

        List<DocumentEntity> attachments = documentMapper.dtosToEntities(dto.getAttachments());
        postEntity.setAttachments(attachments);
        postEntity.setType(dto.getType());
        Optional<UserEntity> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            postEntity.setUser(user);
        }
        else{
            postEntity.setUser(null);
        }


        return postEntity;
    }
}
