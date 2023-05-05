package com.example.MyShroom_backend.dto;

import com.example.MyShroom_backend.entity.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data

public class UpdatePostDto {
    private Long id;

    private String title;

    private String mushroomType;

    private double latitude;

    private double longitude;

    private String description;

    private String base64Img;

    private List<DocumentDto> attachments;

    private Type type;
    private Long userId;
    @JsonCreator
    public UpdatePostDto(@JsonProperty("id") Long id,
                         @JsonProperty("title") String title,
                         @JsonProperty("mushroomType") String mushroomType,
                         @JsonProperty("latitude") double latitude,
                         @JsonProperty("longitude") double longitude,
                         @JsonProperty("description") String description,
                         @JsonProperty("base64Img") String base64Img,
                         @JsonProperty("attachments") List<DocumentDto> attachments,
                         @JsonProperty("type") Type type,
                         @JsonProperty("userId") Long userId){
        System.out.println("la update post dto in constructor");
        this.id = id;
        this.title = title;
        this.mushroomType = mushroomType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.base64Img = base64Img;
        this.attachments = attachments;
        this.userId = userId;
        this.type = type;
    }
}