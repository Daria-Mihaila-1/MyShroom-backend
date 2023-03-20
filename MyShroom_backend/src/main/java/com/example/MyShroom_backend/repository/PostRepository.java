package com.example.MyShroom_backend.repository;

import com.example.MyShroom_backend.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {

}
