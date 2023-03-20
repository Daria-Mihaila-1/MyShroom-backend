package com.example.MyShroom_backend.controller;

import com.example.MyShroom_backend.dto.PostDto;
import com.example.MyShroom_backend.dto.RegisterResponseDto;
import com.example.MyShroom_backend.dto.UploadPostDto;
import com.example.MyShroom_backend.dto.UserDto;
import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor

@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;
    @GetMapping("/get-all")
    public ResponseEntity<List<PostDto>> getAll(){
        System.out.println("intra in get all postss");

        return ResponseEntity.ok(postService.findAll());
    }

    @PostMapping("/upload")
    public ResponseEntity<PostDto> uploadPost(@RequestBody UploadPostDto dto){
        System.out.println("da macar sunt aici");

        return ResponseEntity.ok(postService.addPost(dto));
    }


}
