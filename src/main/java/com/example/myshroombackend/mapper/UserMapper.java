package com.example.myshroombackend.mapper;

import com.example.myshroombackend.dto.LoginResponseDto;
import com.example.myshroombackend.dto.UserDto;
import com.example.myshroombackend.entity.UserEntity;
import org.apache.catalina.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    LoginResponseDto entityToResponseDto(UserEntity entity);
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "userName", target = "userName"),
            @Mapping(source = "password", target="password"),
            @Mapping(source = "rank", target = "rank")
    })
    UserDto entityToDto(UserEntity entity);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "firstName", target = "firstName"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "userName", target = "userName"),
            @Mapping(source = "password", target="password"),
            @Mapping(source = "rank", target = "rank")
    })

    UserEntity dtoToEntity(UserDto dto);
    List<UserEntity> dtosToEntities(List<UserDto> dtos);
   List<UserDto> entitiesToDtos(List<UserEntity> entities);
}
