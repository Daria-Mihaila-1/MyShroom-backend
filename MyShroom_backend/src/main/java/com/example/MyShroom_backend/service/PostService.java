package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.PostDto;
import com.example.MyShroom_backend.dto.UpdatePostDto;
import com.example.MyShroom_backend.dto.UploadPostDto;
import com.example.MyShroom_backend.entity.PostEntity;
import org.hibernate.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PostService {
    public List<PostDto> findAll();
    public Optional<PostEntity> findById(Long id);
    public PostDto addPost(UploadPostDto newDto);
    public PostDto deletePost(Long id) throws ObjectNotFoundException;

    public PostDto updatePost(UpdatePostDto newPostDto);


    List<PostDto> getMyPosts(Long id);

    List<PostDto> getPostsNotReportedBy(Long id);

    List<PostDto> getPostsReportedBy(Long id);
}
