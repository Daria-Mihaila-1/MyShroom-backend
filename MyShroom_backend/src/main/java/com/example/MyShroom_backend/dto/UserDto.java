package com.example.MyShroom_backend.dto;


import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.entity.Rank;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDto {
//TODO: check out if it's necessary
    private Long id;

    private String firstName;

    private String lastName;

    private String userName;


    private Rank rank;
    private int strikes;

    private List<PostDto> reportedPosts;

}
