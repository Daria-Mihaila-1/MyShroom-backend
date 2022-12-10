package com.example.myshroombackend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginRequestDto {
    private String userName;
    private String password;

    public LoginRequestDto(String userName) {
        this.userName = userName;
    }
    @JsonCreator
    public LoginRequestDto(@JsonProperty("userName") String userName, @JsonProperty("password") String password) {
        this.userName = userName;
        this.password = password;
    }
}
