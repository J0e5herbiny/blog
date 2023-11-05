package com.joe.project.repository;

import com.joe.project.entity.Article;
import com.joe.project.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    Optional<Article> findById(UUID id);

    @NonNull
    default Article getById(@NonNull UUID uuid){
        return findById(uuid).orElseThrow(RecordNotFoundException::new);
    }


}
