package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.DocumentDto;
import com.example.MyShroom_backend.dto.PostDto;
import com.example.MyShroom_backend.dto.UploadPostDto;
import org.hibernate.ObjectNotFoundException;

import java.util.List;

public interface PostService {
    public List<PostDto> findAll();
    public PostDto addPost(UploadPostDto newDto);
    public PostDto deletePost(Long id) throws ObjectNotFoundException;

    public PostDto updatePost(PostDto newPostDto);

    PostDto addAttachments(Long id, List<DocumentDto> dto);

    PostDto deleteAttachments(Long id, List<Long> ids);
}
