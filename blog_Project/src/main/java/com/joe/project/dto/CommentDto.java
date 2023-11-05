package com.joe.project.dto;

import java.util.UUID;

public class CommentDto {

//    private Long id;
    private UUID id;
    private String body;

    public CommentDto(String body) {
        this.body = body;
    }

    public CommentDto(UUID id,
                      String body) {
        this.id = id;
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
