package com.example.MyShroom_backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DocumentsDto {
    private Long postId;
    private List<DocumentDto> documents;

    @JsonCreator
    public DocumentsDto(@JsonProperty("postId") Long postId, @JsonProperty("documents") List<DocumentDto> documents)
    {
        this.postId = postId;
        this.documents = documents;
    }

}
