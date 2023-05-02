package com.example.MyShroom_backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ClassifierResponseDto {
    private Map<String, Double> scores;

    @JsonCreator
    public ClassifierResponseDto(@JsonProperty("scores") Map<String, Double> scores){
        this.scores = scores;

    }

}
