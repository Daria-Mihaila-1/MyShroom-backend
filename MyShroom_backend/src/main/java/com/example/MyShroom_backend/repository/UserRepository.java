package com.example.MyShroom_backend.repository;
import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);
    Optional<List<UserEntity>> findAllByReportedPostsContaining(PostEntity postEntity);
}

