package com.example.MyShroom_backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReportPostDto {

    Long userId;
    Long postId;
    @JsonCreator
    public ReportPostDto(@JsonProperty("reporterUserId") Long userId, @JsonProperty("postId") Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
