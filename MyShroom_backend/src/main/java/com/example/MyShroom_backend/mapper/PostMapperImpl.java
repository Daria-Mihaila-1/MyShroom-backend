package com.example.MyShroom_backend.mapper;

import com.example.MyShroom_backend.dto.PostDto;
import com.example.MyShroom_backend.dto.UploadPostDto;
import com.example.MyShroom_backend.entity.PostEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDto entityToDto(PostEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String mushroomType = null;
        float latitude = 0.0f;
        float longitude = 0.0f;
        String description = null;
        String img = null;
        String date = null;
        String time = null;

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

        return new PostDto( id, title, mushroomType, latitude, longitude, description, img, date, time );
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

        return postEntity;
    }

    @Override
    public PostEntity uploadDtoToEntity(UploadPostDto dto) {
        if ( dto == null ) {
            return null;
        }

        PostEntity postEntity = new PostEntity();

//        postEntity.setId( dto.getId() );
        postEntity.setTitle( dto.getTitle() );
        postEntity.setMushroomType( dto.getMushroomType() );
        postEntity.setLatitude((float) dto.getLatitude());
        postEntity.setLongitude((float) dto.getLongitude());
        postEntity.setDescription( dto.getDescription() );
        String imgEncoded = dto.getBase64Img();
        if ( imgEncoded != null ) {
            byte[] decodedImgBytes = Base64.getDecoder().decode(imgEncoded);
            postEntity.setImg(decodedImgBytes);
        }
        postEntity.setDate(LocalDate.now());
        postEntity.setTime(LocalTime.now());

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
}
