package com.example.myshroombackend.dto;

import com.example.myshroombackend.entity.Rank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private Rank rank;
}
