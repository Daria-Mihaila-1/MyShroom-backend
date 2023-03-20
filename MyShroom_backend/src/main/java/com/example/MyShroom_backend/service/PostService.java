package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.PostDto;
import com.example.MyShroom_backend.dto.UploadPostDto;

import java.util.List;

public interface PostService {
    public List<PostDto> findAll();
    public PostDto addPost(UploadPostDto newDto);
}
