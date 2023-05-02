package com.example.MyShroom_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    private double latitude;

    @Column
    private double longitude;

    @Column
    private String description;

    @Column
    private byte[] img;

    @Column
    private LocalDate date;

    @Column
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;


    @OneToMany(mappedBy = "post",cascade=CascadeType.ALL, orphanRemoval = true)
    private List<DocumentEntity> attachments = new ArrayList<>();

    public void setAttachments(List<DocumentEntity> attachments) {
        this.attachments.clear();
        this.attachments.addAll(attachments);
    }
}
