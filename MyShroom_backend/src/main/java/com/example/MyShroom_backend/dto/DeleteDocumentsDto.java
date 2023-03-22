package com.example.MyShroom_backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class DeleteDocumentsDto {
    private Long postId;
    private List<Long> ids;

    @JsonCreator
    public DeleteDocumentsDto(@JsonProperty("postId") Long postId, @JsonProperty("ids") List<Long> ids) {
        this.postId = postId;
        this.ids = ids;
    }
}
