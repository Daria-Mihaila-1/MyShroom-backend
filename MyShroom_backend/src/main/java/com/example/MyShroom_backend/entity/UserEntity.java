package com.example.MyShroom_backend.entity;


import com.example.MyShroom_backend.enums.Rank;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private Rank rank;

    @Column
    private int strikes;

    @Column
    private LocalDate registerDate;
    @Column
    private int profileImageIndex;

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL, orphanRemoval = true)
    private List<PostEntity> posts = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "posts_reported_by_users",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "post_id") }
    )
    private List<PostEntity> reportedPosts = new ArrayList<>();

    public void reportPost(PostEntity postEntity) {
        this.reportedPosts.add(postEntity);
    }


}
