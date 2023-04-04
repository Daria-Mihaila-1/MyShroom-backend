package com.example.MyShroom_backend.dto;

import lombok.Data;

@Data
public class ClassifierResponseDto {
    private String scores;

    public ClassifierResponseDto(String scores){this.scores = scores;}

}
