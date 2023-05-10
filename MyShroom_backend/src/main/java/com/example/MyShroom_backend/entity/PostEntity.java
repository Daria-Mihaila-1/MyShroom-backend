package com.example.MyShroom_backend.entity;

import com.example.MyShroom_backend.enums.MushroomType;
import com.example.MyShroom_backend.enums.Type;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
    private MushroomType mushroomType;

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
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;


    @OneToMany(mappedBy = "post",cascade=CascadeType.ALL, orphanRemoval = true)
    private List<DocumentEntity> attachments = new ArrayList<>();

    @Column
    private Type type;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "reportedPosts")
    private List<UserEntity> reportingUsers = new ArrayList<>();

    @Column(name = "predicted_mushroom_type")
    private MushroomType predicted_mushroom_type;

    public void setAttachments(List<DocumentEntity> attachments) {
        this.attachments.clear();
        this.attachments.addAll(attachments);
    }

}
