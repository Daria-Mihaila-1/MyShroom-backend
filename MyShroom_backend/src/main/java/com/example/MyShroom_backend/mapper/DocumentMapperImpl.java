package com.example.MyShroom_backend.mapper;

import com.example.MyShroom_backend.dto.DocumentDto;
import com.example.MyShroom_backend.entity.DocumentEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
@Component
public class DocumentMapperImpl implements DocumentMapper{

        @Override
        public DocumentDto entityToDto(DocumentEntity entity) {
            if ( entity == null ) {
                return null;
            }

            DocumentDto documentDto = new DocumentDto();

            documentDto.setId( entity.getId() );
            documentDto.setDocName( entity.getDocName() );
            byte[] document = entity.getDocument();
            if ( document != null ) {
                    documentDto.setDocumentBase64(Base64.getEncoder().encodeToString(document));
            }

            return documentDto;
        }

        @Override
        public List<DocumentDto> entitiesToDtos(List<DocumentEntity> entities) {
            if ( entities == null ) {
                return null;
            }

            List<DocumentDto> list = new ArrayList<DocumentDto>( entities.size() );
            for ( DocumentEntity documentEntity : entities ) {
                list.add( entityToDto( documentEntity ) );
            }

            return list;
        }

        @Override
        public DocumentEntity dtoToEntity(DocumentDto dto) {
            if ( dto == null ) {
                return null;
            }

            DocumentEntity documentEntity = new DocumentEntity();

            documentEntity.setId( dto.getId() );
            documentEntity.setDocName( dto.getDocName() );
            String documentEncoded = dto.getDocumentBase64();
            if ( documentEncoded != null ) {
                byte[] decodedDocumentBytes = Base64.getDecoder().decode(documentEncoded);
                documentEntity.setDocument(decodedDocumentBytes);
            }
            else{
                System.out.println("Null document");
            }

            return documentEntity;
        }

        @Override
        public List<DocumentEntity> dtosToEntities(List<DocumentDto> dtos) {
            if ( dtos == null ) {
                return null;
            }

            List<DocumentEntity> list = new ArrayList<DocumentEntity>( dtos.size());
            for ( DocumentDto documentDto : dtos ) {
                list.add( dtoToEntity( documentDto ) );
            }

            return list;
        }
}


