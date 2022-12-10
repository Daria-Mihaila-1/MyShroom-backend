package com.example.myshroombackend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponseDto {

    private String token;

    @JsonCreator
    public LoginResponseDto( @JsonProperty("token") String token) {
        this.token = token;
    }
}
