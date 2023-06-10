package com.example.MyShroom_backend.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterRequestDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private int profileImageIndex;

    @JsonCreator
    public RegisterRequestDto(@JsonProperty("firstName") String firstName,
                              @JsonProperty("lastName") String lastName,
                              @JsonProperty("userName") String userName,
                              @JsonProperty("password") String password,
                              @JsonProperty("profileImageIndex") int profileImageIndex) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.profileImageIndex = profileImageIndex;
    }

}
