package com.example.MyShroom_backend.mapper;

import com.example.MyShroom_backend.dto.AddMushroomImgDto;
import com.example.MyShroom_backend.dto.MushroomImgDto;
import com.example.MyShroom_backend.entity.MushroomImgEntity;
import org.mapstruct.Mapper;

import java.util.List;


public interface MushroomImgMapper {

    MushroomImgDto entityToDto(MushroomImgEntity mushroomImgEntity);

    MushroomImgEntity dtoToEntity(MushroomImgDto mushroomImgDto);

    List<MushroomImgDto> entitiesToDtos(List<MushroomImgEntity> mushroomImgEntities);
    List<MushroomImgEntity> dtosToEntities(List<MushroomImgDto> mushroomImgDtos);

    MushroomImgEntity addDtoToEntity(AddMushroomImgDto dto);
}
