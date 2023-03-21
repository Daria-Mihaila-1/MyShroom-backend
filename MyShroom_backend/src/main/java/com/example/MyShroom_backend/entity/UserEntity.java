package com.example.MyShroom_backend.entity;


import jakarta.persistence.*;
import lombok.Data;

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
}
