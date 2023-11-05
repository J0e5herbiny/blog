package com.joe.project.controller;

import com.joe.project.dto.ArticleDto;
import com.joe.project.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleDto> getArticles(){
        return articleService.readArticles();
    }

    @GetMapping(path = "/{articleId}")
    public ArticleDto getArticle(@PathVariable("articleId") UUID id){
        return articleService.readArticle(id);
    }

    @PostMapping
    public void postArticle(@RequestBody ArticleDto articleDto){
        articleService.createArticle(articleDto);
    }

    @PutMapping(path = "/{articleId}")
    public void putArticle(@PathVariable("articleId") UUID id,
                           @Valid @RequestBody ArticleDto articleDto){
        articleService.updateArticle(id, articleDto);
    }

    @DeleteMapping(path = "/{articleId}")
    public void deleteArticle(@PathVariable("articleId") UUID id){
        articleService.deleteArticle(id);
    }

}
