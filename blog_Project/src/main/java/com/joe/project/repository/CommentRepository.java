package com.joe.project.repository;

import com.joe.project.entity.Comment;
import com.joe.project.exception.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Optional<Comment> findById(UUID uuid);

    @NonNull
    default Comment getById(@NonNull UUID uuid){
        return findById(uuid).orElseThrow(RecordNotFoundException::new);
    }
}
