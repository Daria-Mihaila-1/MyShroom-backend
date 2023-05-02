package com.example.MyShroom_backend.mapper;

import com.example.MyShroom_backend.dto.LoginResponseDto;
import com.example.MyShroom_backend.dto.UserDto;
import com.example.MyShroom_backend.entity.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "userName", target = "username"),
            @Mapping(source = "password", target="password"),
            @Mapping(source = "rank", target = "rank"),
            @Mapping(source = "strikes", target = "strikes")

    })
    UserDto entityToDto(UserEntity entity);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "username", target = "userName"),
            @Mapping(source = "password", target="password"),
            @Mapping(source = "rank", target = "rank"),
            @Mapping(source = "strikes", target = "strikes")

    })

    UserEntity dtoToEntity(UserDto dto);
    List<UserEntity> dtosToEntities(List<UserDto> dtos);
   List<UserDto> entitiesToDtos(List<UserEntity> entities);
}
