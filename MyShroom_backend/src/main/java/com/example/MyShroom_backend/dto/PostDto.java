package com.example.MyShroom_backend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class PostDto {

    private Long id;


    private String title;


    private String mushroomType;


    private float latitude;


    private float longitude;


    private String description;

    private String base64Img;

    private String date;

    private String time;

}
