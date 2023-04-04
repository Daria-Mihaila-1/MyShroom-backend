package com.example.MyShroom_backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClassifierRequestDto {

    private String base64Img;

    @JsonCreator
    public ClassifierRequestDto(@JsonProperty("base64Img") String base64Img){
        this.base64Img = base64Img;
    }
}
