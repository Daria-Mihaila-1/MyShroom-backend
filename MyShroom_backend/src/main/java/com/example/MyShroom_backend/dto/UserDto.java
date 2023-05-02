package com.example.MyShroom_backend.dto;


import com.example.MyShroom_backend.entity.Rank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
//TODO: check out if it's necessary
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private Rank rank;
    private int strikes;
}
