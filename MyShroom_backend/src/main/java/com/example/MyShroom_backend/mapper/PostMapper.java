package com.example.MyShroom_backend.mapper;

import com.example.MyShroom_backend.dto.PostDto;
import com.example.MyShroom_backend.dto.UpdatePostDto;
import com.example.MyShroom_backend.dto.UploadPostDto;
import com.example.MyShroom_backend.entity.PostEntity;


import java.util.List;

public interface PostMapper {
    public PostDto entityToDto(PostEntity entity);
    public PostEntity dtoToEntity(PostDto dto);


    PostEntity uploadDtoToEntity(UploadPostDto dto);

    public List<PostEntity> dtosToEntities(List<PostDto> dtos);
    public List<PostDto> entitiesToDtos(List<PostEntity> entities);

    public PostEntity updateDtoToEntity(UpdatePostDto newPostDto);

}
