package com.joe.project.entity;

import com.joe.project.dto.UserDto;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
//@Builder
public class User extends BaseEntity {

//    @SequenceGenerator(
//            name = "user_sequence",
//            sequenceName ="user_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "user_sequence"
//    )
//    @Id
//    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,
                       CascadeType.MERGE,
                       CascadeType.PERSIST,
                       CascadeType.REFRESH},
            mappedBy = "author")
    private List<Article> articles;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH,
                           CascadeType.MERGE,
                           CascadeType.PERSIST,
                           CascadeType.REFRESH})
    @JoinTable(name = "user_followings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id"))
    private Set<User> followings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "followings")
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "userFavorite")
    private Set<Article> favoriteArticle= new HashSet<>();

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name,
                String email,
                String password,
                Collection<Role> roles,
                Article articles,
                Set<User> followings,
                Set<User> followers,
                Set<Article> favoriteArticle) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.articles = (List<Article>) articles;
        this.followings = followings;
        this.followers = followers;
        this.favoriteArticle = favoriteArticle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Set<User> getFollowings() {
        return followings;
    }

    public void setFollowings(Set<User> followings) {
        this.followings = followings;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<Article> getFavoriteArticle() {
        return favoriteArticle;
    }

    public void setFavoriteArticle(Set<Article> favoriteArticle) {
        this.favoriteArticle = favoriteArticle;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", articles=" + articles +
                ", followings=" + followings +
                ", followers=" + followers +
                ", favoriteArticle=" + favoriteArticle +
                ", id=" + id +
                '}';
    }

    public void addArticle(Article article){
        this.articles.add(article);
        article.setAuthor(article.getAuthor());
    }

    public void removeArticle(Article article){
        this.articles.remove(article);
        article.setAuthor(null);

    }

    public void followUser(User followedUser){
        followings.add(followedUser);
    }

    public void unfollowUser(User unfollowedUser){
        followings.remove(unfollowedUser);
    }

    public void favoriteArticle(Article favArticle){
        favoriteArticle.add(favArticle);
    }
    public void unfavoriteArticle(Article unfavArticle){
        favoriteArticle.remove(unfavArticle);
    }

    public UserDto userDto(){
        return new UserDto(
                id,
                name,
                email,
                password);
    }

}
