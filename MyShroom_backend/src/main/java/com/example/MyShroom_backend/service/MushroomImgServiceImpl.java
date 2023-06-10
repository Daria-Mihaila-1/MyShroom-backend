package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.AddMushroomImgDto;
import com.example.MyShroom_backend.dto.MushroomImgDto;
import com.example.MyShroom_backend.entity.MushroomImgEntity;
import com.example.MyShroom_backend.enums.MushroomType;
import com.example.MyShroom_backend.mapper.MushroomImgMapper;
import com.example.MyShroom_backend.repository.MushroomImgRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MushroomImgServiceImpl implements MushroomImgService{
    private final MushroomImgRepository mushroomImgRepository;
    private final MushroomImgMapper mushroomImgMapper;

    @Override
    public List<MushroomImgDto> findAll() {
        return mushroomImgMapper.entitiesToDtos(mushroomImgRepository.findAll());
    }

    @Override
    public List<MushroomImgDto> findAllByMushroomType(MushroomType mushroomType) {
        return mushroomImgMapper.entitiesToDtos(mushroomImgRepository.findAllByMushroomType(mushroomType));
    }

    @Override
    public MushroomImgDto addImg(AddMushroomImgDto dto){
        return this.mushroomImgMapper.entityToDto(this.mushroomImgRepository.save(this.mushroomImgMapper.addDtoToEntity(dto)));

    }

}
