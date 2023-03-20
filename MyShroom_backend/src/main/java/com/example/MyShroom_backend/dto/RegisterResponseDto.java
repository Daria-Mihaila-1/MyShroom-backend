package com.example.MyShroom_backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterResponseDto {
    private String userName;

    @JsonCreator
    public RegisterResponseDto(@JsonProperty("userName") String userName) {
        this.userName = userName;
    }
}
