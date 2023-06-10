package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.AddMushroomImgDto;
import com.example.MyShroom_backend.dto.MushroomImgDto;
import com.example.MyShroom_backend.entity.MushroomImgEntity;
import com.example.MyShroom_backend.enums.MushroomType;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MushroomImgService {
    List<MushroomImgDto> findAll();
    List<MushroomImgDto> findAllByMushroomType(MushroomType mushroomType);

    MushroomImgDto addImg(AddMushroomImgDto dto);
}
