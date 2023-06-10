package com.example.MyShroom_backend.repository;

import com.example.MyShroom_backend.entity.MushroomImgEntity;
import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.enums.MushroomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MushroomImgRepository extends JpaRepository<MushroomImgEntity,Long> {

    List<MushroomImgEntity> findAllByMushroomType(MushroomType mushroomType);
}
