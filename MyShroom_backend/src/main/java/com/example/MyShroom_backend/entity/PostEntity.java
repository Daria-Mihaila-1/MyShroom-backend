package com.example.MyShroom_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name="posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String mushroomType;

    @Column
    private float latitude;

    @Column
    private float longitude;

    @Column
    private String description;

    @Column
    private byte[] img;

    @Column
    private LocalDate date;

    @Column
    private LocalTime time;

}
