package com.example.MyShroom_backend.dto;

import com.example.MyShroom_backend.enums.MushroomType;
import com.example.MyShroom_backend.enums.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {

    private Long id;


    private String title;

    private MushroomType mushroomType;

    private double latitude;

    private double longitude;

    private String description;

    private String base64Img;

    private String date;

    private String time;

    private Type type;
    private Long userId;

    @JsonCreator
    public PostDto(@JsonProperty("id") Long id,
                         @JsonProperty("title") String title,
                         @JsonProperty("mushroomType") MushroomType mushroomType,
                         @JsonProperty("latitude") double latitude,
                         @JsonProperty("longitude") double longitude,
                         @JsonProperty("description") String description,
                        @JsonProperty("base64Img") String base64Img,
                           @JsonProperty("date") String date,
                           @JsonProperty("time") String time,
                         @JsonProperty("type") Type type,
                         @JsonProperty("userId") Long userId){
        System.out.println("la update post dto in constructor");
        this.id = id;
        this.title = title;
        this.mushroomType = mushroomType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.date = date;
        this.time = time;
        this.base64Img = base64Img;
        this.userId = userId;
        this.type = type;
    }
}
