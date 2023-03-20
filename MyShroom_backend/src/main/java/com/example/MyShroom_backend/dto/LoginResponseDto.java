package com.example.MyShroom_backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private Long id;

@JsonCreator
public LoginResponseDto(@JsonProperty("id") Long id, @JsonProperty("token") String token) {
    this.id = id;
    this.token = token;


}
}
