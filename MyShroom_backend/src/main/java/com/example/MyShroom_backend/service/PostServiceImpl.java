package com.example.MyShroom_backend.service;

import com.example.MyShroom_backend.dto.DocumentDto;
import com.example.MyShroom_backend.dto.PostDto;
import com.example.MyShroom_backend.dto.UpdatePostDto;
import com.example.MyShroom_backend.dto.UploadPostDto;
import com.example.MyShroom_backend.entity.DocumentEntity;
import com.example.MyShroom_backend.entity.PostEntity;
import com.example.MyShroom_backend.entity.Rank;
import com.example.MyShroom_backend.entity.UserEntity;
import com.example.MyShroom_backend.mapper.DocumentMapper;
import com.example.MyShroom_backend.mapper.PostMapper;
import com.example.MyShroom_backend.repository.DocumentRepository;
import com.example.MyShroom_backend.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements  PostService{
    private final PostRepository postRepository;
    private final DocumentRepository documentRepository;
    private final PostMapper postMapper;
    private final DocumentMapper documentMapper;

    private final UserService userService;
    @Override
    public List<PostDto> findAll() {
        List<PostEntity> entities = postRepository.findAll();
        return postMapper.entitiesToDtos(entities);
    }

    @Override
    public PostDto addPost(UploadPostDto newDTo) {
        try {
            PostEntity newEntity = postMapper.uploadDtoToEntity(newDTo);
            PostEntity newPostEntity = postRepository.save(newEntity);

            // Increase Rank of user if necessary
            UserEntity userOfPost = this.userService.findById(newDTo.getUserId());
            if (this.postRepository.findAllByUserId(userOfPost.getId()).size() == 4 && userOfPost.getRank() == Rank.BEGINNER) {
                this.userService.increaseRank(userOfPost.getId(), Rank.INTERMEDIATE);
            }
            else if (this.postRepository.findAllByUserId(userOfPost.getId()).size() == 10 && userOfPost.getRank() == Rank.INTERMEDIATE) {
                this.userService.increaseRank(userOfPost.getId(), Rank.EXPERT);
            }
            return (postMapper.entityToDto(newPostEntity));
            //TODO: add check the photo with the classifier and add it to one of the tables
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        }

    @Override
    public PostDto deletePost(Long id) throws EntityNotFoundException {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
        if (optionalPostEntity.isPresent()){
            PostEntity postEntity = optionalPostEntity.get();
            postRepository.deleteById(id);
            return postMapper.entityToDto(postEntity);
        }
        else {
            throw new EntityNotFoundException("Entity not found with id " + id);
        }

    }

    @Override
    public PostDto updatePost( UpdatePostDto newPostDto) throws EntityNotFoundException{

        Optional<PostEntity> optionalPostEntity = postRepository.findById(newPostDto.getId());
        if (optionalPostEntity.isPresent()){

            PostEntity postEntity = this.postMapper.updateDtoToEntity(newPostDto);
            postRepository.save(postEntity);
            PostEntity updatedPostEntity = postRepository.findById(postEntity.getId()).get();
            return postMapper.entityToDto(updatedPostEntity);
        }
        else {
            throw new EntityNotFoundException("Entity not found with id " + newPostDto.getId());
        }
    }

    @Override
    public PostDto addAttachments(Long id, List<DocumentDto> dtos) {
        List<DocumentEntity> documents = documentMapper.dtosToEntities(dtos);
        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
        if (optionalPostEntity.isPresent()){
            PostEntity postEntity = optionalPostEntity.get();
            for (DocumentEntity entity1 : documents){
                entity1.setPost(postEntity);
            }
            documentRepository.saveAll(documents);
            Optional<PostEntity> secondOptionalPostEntity = postRepository.findById(id);
            PostEntity entity = secondOptionalPostEntity.get();
            return postMapper.entityToDto(entity);
        }
        else {
            throw new EntityNotFoundException("Entity not found with id " + id);
        }
    }

    @Override
    @Transactional
    public PostDto deleteAttachments(Long id, List<Long> ids) {

        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);

        if (optionalPostEntity.isPresent()) {
            PostEntity postEntity = optionalPostEntity.get();
            System.out.println("am ajuns aicia");
            List<DocumentEntity> existingDocEntities = documentRepository.findAllByPost(postEntity);
            List<Long> existingDocIds = existingDocEntities
                    .stream()
                    .map(DocumentEntity::getId)
                    .toList();
            System.out.println("am filtrat atachmenturile ca sa fie doar idurile lor");

            if (existingDocIds.containsAll(ids)){
                // Delete the attachments from the documents repo
                List<DocumentEntity> remainingDocEntities = existingDocEntities
                        .stream()
                        .filter(obj -> ! ids.contains(obj.getId()))
                        .toList();

                System.out.println(remainingDocEntities.size());
                postEntity.setAttachments(remainingDocEntities);
                postRepository.save(postEntity);
                documentRepository.deleteAllById(ids);
                Optional<PostEntity> secondOptionalPostEntity = postRepository.findById(id);
                postEntity = secondOptionalPostEntity.get();
                return postMapper.entityToDto(postEntity);

            }
            else {
                throw new EntityNotFoundException("Post does not have all given attachements");
            }
        }
        else {
            throw new EntityNotFoundException("Entity not found with id " + id);
        }
    }

    @Override
    public List<PostDto> getMyPosts(Long id) {
        List<PostEntity> myPosts = postRepository.findAllByUserId(id);
        return postMapper.entitiesToDtos(myPosts);
    }



}
