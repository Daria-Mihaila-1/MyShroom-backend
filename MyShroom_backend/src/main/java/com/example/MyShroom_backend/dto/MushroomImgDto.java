package com.example.MyShroom_backend.dto;

import com.example.MyShroom_backend.enums.MushroomType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class MushroomImgDto {
    private Long id;
    private MushroomType mushroomType;
    private boolean poisonous;
    private String base64Img;


    @JsonCreator
    public MushroomImgDto(@JsonProperty("id") Long id,
                          @JsonProperty("mushroogenusmType") MushroomType mushroomType,
                          @JsonProperty("poisonous") boolean poisonous,
                          @JsonProperty("base64Img") String base64Img) {
        this.id = id;
        this.mushroomType = mushroomType;
        this.poisonous = poisonous;
        this.base64Img = base64Img;
    }
}
