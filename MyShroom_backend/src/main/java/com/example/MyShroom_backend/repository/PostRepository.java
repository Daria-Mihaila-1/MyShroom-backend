package com.example.MyShroom_backend.repository;

import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
    public List<PostEntity> findAllByUserId(Long id);
    public List<PostEntity> findAllByIdNotIn(List<Long> reportedPostsIds);

}
