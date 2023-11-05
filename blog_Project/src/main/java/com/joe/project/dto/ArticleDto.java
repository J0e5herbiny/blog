package com.joe.project.dto;


import java.util.UUID;

public class ArticleDto {

//    private Long id;
    private UUID id;
    private String title;
    private String body;

    public ArticleDto(UUID id,
                      String title,
                      String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
