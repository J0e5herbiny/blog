package com.joe.project.repositoryTest;

import com.joe.project.entity.Article;
import com.joe.project.entity.User;
import com.joe.project.repository.ArticleRepository;
import com.joe.project.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepositoryTest;
    @Autowired
    private UserRepository userRepositoryTest;

    @AfterEach
    void tearDown(){
        articleRepositoryTest.deleteAll();
    }

    @Test
    void ifArticleExists(){
        //Given
        User user = new User("joe", "joe@email", "123");
        userRepositoryTest.save(user);
        Article article = new Article("article", "a lots of words", user);
        articleRepositoryTest.save(article);
        //When
        boolean expected = articleRepositoryTest.existsById(article.getId());
        //Then
        assertThat(expected).isTrue();
    }

}
