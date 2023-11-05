package com.joe.project.entity;

import com.joe.project.dto.ArticleDto;
import lombok.Builder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Builder
public class Article extends BaseEntity {

//    @Id
//    @SequenceGenerator(
//            name = "article_sequence",
//            sequenceName = "article_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "article_sequence"
//    )
//    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade ={CascadeType.DETACH,
                      CascadeType.MERGE,
                      CascadeType.PERSIST,
                      CascadeType.REFRESH})
    @JoinTable(
            name = "article_favorites",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userFavorite = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "article")
    private Set<Comment> comments = new HashSet<>();

    public Article() {
    }

    public Article(String title,
                   String body) {
        this.title = title;
        this.body = body;
    }

    public Article(String title,
                   String body,
                   User author) {
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public Article(String title,
                   String body,
                   User author,
                   Set<User> userFavorite,
                   Set<Comment> comments) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.userFavorite = userFavorite;
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Set<User> getUserFavorite() {
        return userFavorite;
    }

    public void setUserFavorite(Set<User> userFavorite) {
        this.userFavorite = userFavorite;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", author=" + author +
                ", userFavorite=" + userFavorite +
                ", comments=" + comments +
                ", id=" + id +
                '}';
    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setArticle(comment.getArticle());
    }
    public void removeComment(Comment comment){
        comments.remove(comment);
        comment.setArticle(null);
    }

    public ArticleDto articleDto(){
        return new ArticleDto(id, title, body );
    }

    public void update(String title, String body){
        this.title= title;
        this.body= body;
    }
}
