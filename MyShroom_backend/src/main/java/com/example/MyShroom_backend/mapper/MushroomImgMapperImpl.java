package com.example.MyShroom_backend.mapper;

import com.example.MyShroom_backend.dto.AddMushroomImgDto;
import com.example.MyShroom_backend.dto.MushroomImgDto;
import com.example.MyShroom_backend.entity.MushroomImgEntity;
import com.example.MyShroom_backend.enums.MushroomType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
@Component
@AllArgsConstructor
public class MushroomImgMapperImpl implements MushroomImgMapper{

        @Override
        public MushroomImgDto entityToDto(MushroomImgEntity mushroomImgEntity) {
            if ( mushroomImgEntity == null ) {
                return null;
            }

            Long id = null;
            MushroomType mushroomType = null;
            boolean poisonous = false;

            id = mushroomImgEntity.getId();
            mushroomType = mushroomImgEntity.getMushroomType();
            poisonous = mushroomImgEntity.isPoisonous();

            String base64Img = Base64.getEncoder().encodeToString(mushroomImgEntity.getImg());;


            MushroomImgDto mushroomImgDto = new MushroomImgDto( id, mushroomType, poisonous, base64Img );

            return mushroomImgDto;
        }

        @Override
        public MushroomImgEntity dtoToEntity(MushroomImgDto mushroomImgDto) {
            if ( mushroomImgDto == null ) {
                return null;
            }

            MushroomImgEntity mushroomImgEntity = new MushroomImgEntity();

            mushroomImgEntity.setId( mushroomImgDto.getId() );
            mushroomImgEntity.setMushroomType( mushroomImgDto.getMushroomType() );
            mushroomImgEntity.setPoisonous( mushroomImgDto.isPoisonous() );
            byte[] decodedImgBytes = Base64.getDecoder().decode(mushroomImgDto.getBase64Img());
            mushroomImgEntity.setImg(decodedImgBytes);

            return mushroomImgEntity;
        }

        @Override
        public List<MushroomImgDto> entitiesToDtos(List<MushroomImgEntity> mushroomImgEntities) {
            if ( mushroomImgEntities == null ) {
                return null;
            }

            List<MushroomImgDto> list = new ArrayList<MushroomImgDto>( mushroomImgEntities.size() );
            for ( MushroomImgEntity mushroomImgEntity : mushroomImgEntities ) {
                list.add( entityToDto( mushroomImgEntity ) );
            }

            return list;
        }

        @Override
        public List<MushroomImgEntity> dtosToEntities(List<MushroomImgDto> mushroomImgDtos) {
            if ( mushroomImgDtos == null ) {
                return null;
            }

            List<MushroomImgEntity> list = new ArrayList<MushroomImgEntity>( mushroomImgDtos.size() );
            for ( MushroomImgDto mushroomImgDto : mushroomImgDtos ) {
                list.add( dtoToEntity( mushroomImgDto ) );
            }

            return list;
        }

    @Override
    public MushroomImgEntity addDtoToEntity(AddMushroomImgDto dto) {

        if (dto == null) {
            return null;
        }

        MushroomImgEntity mushroomImgEntity = new MushroomImgEntity();

        mushroomImgEntity.setMushroomType( dto.getMushroomType() );
        mushroomImgEntity.setPoisonous( dto.isPoisonous() );
        byte[] decodedImgBytes = Base64.getDecoder().decode(dto.getBase64Img());
        mushroomImgEntity.setImg(decodedImgBytes);

        return mushroomImgEntity;
    }
}

