package com.example.MyShroom_backend.entity;

import com.example.MyShroom_backend.enums.MushroomType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="mushroom_imgs")
public class MushroomImgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    MushroomType mushroomType;

    @Column
    boolean poisonous;

    @Column
    byte[] img;


}
