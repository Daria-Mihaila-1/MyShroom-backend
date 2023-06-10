package com.example.MyShroom_backend.mapper;

import com.example.MyShroom_backend.dto.LoginResponseDto;
import com.example.MyShroom_backend.dto.UserDto;
import com.example.MyShroom_backend.entity.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses=PostMapper.class)
public interface UserMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "userName", target = "userName"),
            @Mapping(source = "rank", target = "rank"),
            @Mapping(source = "strikes", target = "strikes"),
            @Mapping(source = "reportedPosts", target= "reportedPosts"),
            @Mapping(source = "registerDate", target= "registerDate"),
            @Mapping(source = "profileImageIndex", target= "profileImageIndex")

    })
    UserDto entityToDto(UserEntity entity);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "userName", target = "userName"),
            @Mapping(source = "rank", target = "rank"),
            @Mapping(source = "strikes", target = "strikes"),
            @Mapping(source = "reportedPosts", target= "reportedPosts"),
            @Mapping(source = "registerDate", target= "registerDate"),
            @Mapping(source = "profileImageIndex", target= "profileImageIndex")

    })

    UserEntity dtoToEntity(UserDto dto);
    List<UserEntity> dtosToEntities(List<UserDto> dtos);
   List<UserDto> entitiesToDtos(List<UserEntity> entities);
}
