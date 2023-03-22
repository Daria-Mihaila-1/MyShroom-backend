package com.example.MyShroom_backend.mapper;

import com.example.MyShroom_backend.dto.DocumentDto;
import com.example.MyShroom_backend.entity.DocumentEntity;
import org.mapstruct.Mapper;

import java.util.List;


public interface DocumentMapper {
    public DocumentDto entityToDto(DocumentEntity entity);
    public List<DocumentDto> entitiesToDtos(List<DocumentEntity> entities);
    public DocumentEntity dtoToEntity(DocumentDto dto);
    public List<DocumentEntity> dtosToEntities(List<DocumentDto> dtos);

}
