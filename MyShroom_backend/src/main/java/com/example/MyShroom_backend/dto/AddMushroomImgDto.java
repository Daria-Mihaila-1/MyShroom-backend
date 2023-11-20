package com.example.MyShroom_backend.dto;

import com.example.MyShroom_backend.enums.MushroomType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddMushroomImgDto {
    private MushroomType mushroomType;
    private boolean poisonous;

    private String base64Img;


    @JsonCreator
    public AddMushroomImgDto(
                          @JsonProperty("mushroomType") MushroomType mushroomType,
                          @JsonProperty("poisonous") boolean poisonous,
                          @JsonProperty("base64Img") String base64Img) {
        this.mushroomType = mushroomType;
        this.poisonous = poisonous;
        this.base64Img = base64Img;
    }
}
