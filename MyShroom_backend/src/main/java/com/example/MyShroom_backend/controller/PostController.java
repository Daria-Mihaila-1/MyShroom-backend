package com.example.MyShroom_backend.controller;

import com.example.MyShroom_backend.dto.*;
import com.example.MyShroom_backend.entity.Type;
import com.example.MyShroom_backend.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try{
            return ResponseEntity.ok( postService.deletePost(id));
        }
        catch (EntityNotFoundException  e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePost(@RequestBody UpdatePostDto dto) {
        try{
            return ResponseEntity.ok( postService.updatePost(dto));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/add-attachments")
    public ResponseEntity<?> addAttachments(@RequestBody DocumentsDto dto) {
        return ResponseEntity.ok(postService.addAttachments(dto.getPostId(), dto.getDocuments()));

    }
   @DeleteMapping("/delete-attachments")
    public ResponseEntity<?> deleteAttachments(@RequestBody DeleteDocumentsDto dto) {
        try{
            return ResponseEntity.ok(postService.deleteAttachments(dto.getPostId(), dto.getIds()));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

   }

   @GetMapping("/get-my-posts/{id}")
    public ResponseEntity<?> getMyPosts(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getMyPosts(id));
   }


   @GetMapping("get-post-types")
    public ResponseEntity<?> getPostTypes() {
        return ResponseEntity.ok(Type.values());
   }


    @GetMapping("/get-posts-not-reported-by/{id}")
    public ResponseEntity<?> getPostsNotReportedBy(@PathVariable Long id) {
        return ResponseEntity.ok(this.postService.getPostsNotReportedBy(id));
    }
}
