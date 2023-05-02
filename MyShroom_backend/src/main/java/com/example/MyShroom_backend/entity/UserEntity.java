package com.example.MyShroom_backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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

    @OneToMany(cascade = CascadeType.ALL)

    @JoinColumn(name="user_id")
    private Set<PostEntity> posts = new TreeSet<PostEntity>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }
    )
    @JoinTable(name = "posts_reported_by_users",joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "post_id") }
    )
    private Set<PostEntity> reported_posts = new HashSet<>();


}
