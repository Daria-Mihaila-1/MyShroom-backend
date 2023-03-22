package com.example.MyShroom_backend.dto;

import com.example.MyShroom_backend.entity.DocumentEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.print.Doc;

@Data
@RequiredArgsConstructor
public class DocumentDto {

    private Long id;

    private String docName;


    private String documentBase64;


    @JsonCreator
    public DocumentDto(@JsonProperty("docName") String docName, @JsonProperty("documentBase64") String documentBase64)
    {
        this.docName = docName;
        this.documentBase64 = documentBase64;
    }
}
