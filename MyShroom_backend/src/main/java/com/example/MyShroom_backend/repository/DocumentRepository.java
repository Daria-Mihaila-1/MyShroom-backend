package com.example.MyShroom_backend.repository;

import com.example.MyShroom_backend.entity.DocumentEntity;
import com.example.MyShroom_backend.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    List<DocumentEntity> findAllByPost(PostEntity postEntity);
}
