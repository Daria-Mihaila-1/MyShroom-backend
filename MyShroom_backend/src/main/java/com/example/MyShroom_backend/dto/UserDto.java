package com.example.MyShroom_backend.dto;


import com.example.MyShroom_backend.enums.Rank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

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

    private String registerDate;

    private int profileImageIndex;

}
