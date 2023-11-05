package com.joe.project.service;

import com.joe.project.dto.ArticleDto;
import com.joe.project.entity.Article;
import com.joe.project.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void createArticle(ArticleDto articleDto){
        Optional<Article> articleOptional =
                articleRepository.findById(articleDto.getId());
        if (articleOptional.isPresent()){
            throw new IllegalStateException("This article is repeated !");
        }
        Article article = new Article(articleDto.getTitle(), articleDto.getBody());
        articleRepository.save( article );
    }

    public List<ArticleDto> readArticles(){
        List<Article> articles = articleRepository.findAll();
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article : articles) {
            articleDtos.add(article.articleDto());
        }
        return articleDtos;
    }

//    public Optional<ArticleDto> readArticle(Long id){
//         return Optional.ofNullable(articleRepository.getById(id).articleDto());
//    }
    public ArticleDto readArticle(UUID id){
        return articleRepository.getById(id).articleDto();
    }

//    @Transactional
//    public void updateArticle(Long id,
//                              String title,
//                              String body){
//        Article article = articleRepository.findById(id)
//                .orElseThrow( ()->new IllegalStateException("This article with this id:"+id+"doesn't exist.") );
//        if (title != null && title.length() > 0 && !Objects.equals(article.getTitle(), title)){
//            article.setTitle(title);
//        }
//
//        if (body != null && body.length() > 0 && Objects.equals(article.getBody(), body) ){
//            article.setBody(body);
//        }
//    }

    @Transactional
    public void updateArticle(UUID id,
                              ArticleDto articleDto){

        Article article = articleRepository.findById(id)
                .orElseThrow( ()->new IllegalStateException("This article with this id:"+id+"doesn't exist.") );

        if (article != null && !Objects.equals(article, articleDto)){
            article.setTitle(articleDto.getTitle());
            article.setBody(articleDto.getBody());
        }

    }

    public void deleteArticle(UUID id){
        boolean articleExists = articleRepository.existsById(id);
        if (!articleExists){
            throw new IllegalStateException("This article doesn't exist !");
        }
        articleRepository.deleteById(id);
    }

}
