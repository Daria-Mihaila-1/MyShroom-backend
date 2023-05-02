package com.example.MyShroom_backend.dto;

import com.example.MyShroom_backend.entity.DocumentEntity;
import com.example.MyShroom_backend.entity.Type;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PostDto {

    private Long id;


    private String title;

    private String mushroomType;

    private double latitude;

    private double longitude;

    private String description;

    private String base64Img;

    private String date;

    private String time;
    private List<DocumentDto> attachments;

    private Type type;
    private UserDto user;


}
