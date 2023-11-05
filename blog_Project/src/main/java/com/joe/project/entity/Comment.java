package com.joe.project.entity;

import com.joe.project.dto.CommentDto;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table
@Builder
public class Comment extends BaseEntity {

    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public Comment() {
    }

    public Comment(String body) {
        this.body = body;
    }

    public Comment(String body,
                   User author,
                   Article article) {
        this.body = body;
        this.author = author;
        this.article = article;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "body='" + body + '\'' +
                ", author=" + author +
                ", article=" + article +
                ", id=" + id +
                '}';
    }

    public CommentDto commentDto(){
        return new CommentDto(this.id, this.body);
    }

    public void update(String body){
        this.body= body;
    }

}
