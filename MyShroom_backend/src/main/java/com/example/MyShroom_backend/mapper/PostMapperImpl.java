package com.example.MyShroom_backend.mapper;

import com.example.MyShroom_backend.dto.*;
import com.example.MyShroom_backend.entity.DocumentEntity;
import com.example.MyShroom_backend.entity.PostEntity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

import com.example.MyShroom_backend.enums.MushroomType;
import com.example.MyShroom_backend.enums.Type;
import com.example.MyShroom_backend.entity.UserEntity;
import com.example.MyShroom_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;

import static java.lang.System.in;


@Component
@AllArgsConstructor
public class PostMapperImpl implements PostMapper {

    private final UserRepository userRepository;
    private final DocumentMapper documentMapper;

    @Override
    public PostDto entityToDto(PostEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        MushroomType mushroomType = null;
        double latitude = 0.0f;
        double longitude = 0.0f;
        String description = null;
        String img = null;
        String date = null;
        String time = null;
        List<DocumentDto> attachments = null;
        Long userId = null;
        Type type = null;

        id = entity.getId();
        title = entity.getTitle();
        mushroomType = entity.getMushroomType();
        latitude = entity.getLatitude();
        longitude = entity.getLongitude();
        description = entity.getDescription();
        byte[] img1 = entity.getImg();
        if ( img1 != null ) {
            img = Base64.getEncoder().encodeToString(img1);
        }
        date = entity.getDate().toString();
        time = entity.getTime().toString();
        attachments = documentMapper.entitiesToDtos(entity.getAttachments());
        userId = entity.getUser().getId();
        type = entity.getType();

        return new PostDto( id, title, mushroomType, latitude, longitude, description, img, date, time,attachments, type, userId);
    }

    @Override
    public PostEntity dtoToEntity(PostDto dto) {
        if ( dto == null ) {
            return null;
        }

        PostEntity postEntity = new PostEntity();

        postEntity.setId( dto.getId() );
        postEntity.setTitle( dto.getTitle() );
        postEntity.setMushroomType( dto.getMushroomType() );
        postEntity.setLatitude( dto.getLatitude() );
        postEntity.setLongitude( dto.getLongitude() );
        postEntity.setDescription( dto.getDescription() );
        String imgEncoded = dto.getBase64Img();
        if ( imgEncoded != null ) {
            byte[] decodedImgBytes = Base64.getDecoder().decode(imgEncoded);
            postEntity.setImg(decodedImgBytes);
        }
        postEntity.setDate(LocalDate.parse(dto.getDate()));
        postEntity.setTime(LocalTime.parse(dto.getTime()));
        List<DocumentEntity> attachments = documentMapper.dtosToEntities(dto.getAttachments());
        postEntity.setAttachments(attachments);
        postEntity.setType(dto.getType());


        Optional<UserEntity> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            postEntity.setUser(user);
        }
        else{
            postEntity.setUser(null);
        }

        return postEntity;
    }

    @Override
    public PostEntity uploadDtoToEntity(UploadPostDto dto) {
        if ( dto == null ) {
            return null;
        }

        PostEntity postEntity = new PostEntity();

        postEntity.setTitle( dto.getTitle() );
        postEntity.setMushroomType( dto.getMushroomType() );
        postEntity.setLatitude((float) dto.getLatitude());
        postEntity.setLongitude((float) dto.getLongitude());
        postEntity.setDescription( dto.getDescription() );
        String imgEncoded = dto.getBase64Img();
        if ( imgEncoded != null ) {
            byte[] decodedImgBytes = Base64.getDecoder().decode(imgEncoded);

            System.out.println(scaleToSize(decodedImgBytes, 300, 300));
            System.out.println(decodedImgBytes);
            postEntity.setImg(decodedImgBytes);
        }
        postEntity.setDate(LocalDate.now());
        postEntity.setTime(LocalTime.now());

        List<DocumentEntity> attachments = documentMapper.dtosToEntities(dto.getAttachments());
        postEntity.setAttachments(attachments);
        postEntity.setType(dto.getType());
        Optional<UserEntity> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            postEntity.setUser(user);
        }
        else{
            postEntity.setUser(null);
        }


        return postEntity;
    }

    public byte[] scaleToSize(byte[] imgFile, int width, int height) {

        ByteArrayInputStream in = new ByteArrayInputStream(imgFile);
        try {
            BufferedImage img = ImageIO.read(in);
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            System.out.println(bufferedImage.getGraphics());
            bufferedImage.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            System.out.println(bufferedImage.getGraphics());
            ImageIO.write(bufferedImage, "jpg", buffer);

            return buffer.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PostEntity> dtosToEntities(List<PostDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<PostEntity> list = new ArrayList<PostEntity>( dtos.size() );
        for ( PostDto postDto : dtos ) {
            list.add( dtoToEntity( postDto ) );
        }

        return list;
    }

    @Override
    public List<PostDto> entitiesToDtos(List<PostEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PostDto> list = new ArrayList<PostDto>( entities.size() );
        for ( PostEntity postEntity : entities ) {
            list.add( entityToDto( postEntity ) );
        }

        return list;
    }

    @Override
    public PostEntity updateDtoToEntity(UpdatePostDto dto) {
        if ( dto == null ) {
            return null;
        }
        PostEntity postEntity = new PostEntity();
        postEntity.setId(dto.getId());
        postEntity.setTitle( dto.getTitle() );
        postEntity.setMushroomType( dto.getMushroomType() );
        postEntity.setLatitude((float) dto.getLatitude());
        postEntity.setLongitude((float) dto.getLongitude());
        postEntity.setDescription( dto.getDescription() );
        String imgEncoded = dto.getBase64Img();
        if ( imgEncoded != null ) {
            byte[] decodedImgBytes = Base64.getDecoder().decode(imgEncoded);
            System.out.println(decodedImgBytes);
            postEntity.setImg(decodedImgBytes);
        }
        postEntity.setDate(LocalDate.now());
        postEntity.setTime(LocalTime.now());

        List<DocumentEntity> attachments = documentMapper.dtosToEntities(dto.getAttachments());
        postEntity.setAttachments(attachments);
        postEntity.setType(dto.getType());
        Optional<UserEntity> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            postEntity.setUser(user);
        }
        else{
            postEntity.setUser(null);
        }

        return postEntity;
    }
}
