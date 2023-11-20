package com.example.MyShroom_backend.dto;

import com.example.MyShroom_backend.enums.MushroomType;
import com.example.MyShroom_backend.enums.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UploadPostDto {

    private String title;

    private MushroomType mushroomType;

    private double latitude;

    private double longitude;

    private String description;

    private String base64Img;

    private Long userId;
    private Type type;
    @JsonCreator
    public UploadPostDto(@JsonProperty("title") String title, @JsonProperty("mushroomType") MushroomType mushroomType,
                         @JsonProperty("latitude") double latitude, @JsonProperty("longitude") double longitude,
                         @JsonProperty("description") String description, @JsonProperty("base64Img") String base64Img
            , @JsonProperty("userId") Long userId,  @JsonProperty("type") Type type ){
        this.title = title;
        this.mushroomType = mushroomType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.base64Img = base64Img;
        this.userId = userId;
        this.type = type;
    }
}
