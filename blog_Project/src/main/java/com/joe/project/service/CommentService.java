package com.joe.project.service;

import com.joe.project.dto.CommentDto;
import com.joe.project.entity.Comment;
import com.joe.project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    public void createComment(CommentDto commentDto){
        Comment comment = new Comment(commentDto.getBody());
        commentRepository.save(comment).commentDto();
    }

    public List<CommentDto> readComments(){

        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentDtos = new ArrayList<>();
        for ( Comment comment: comments ) {
            commentDtos.add(comment.commentDto());
        }
        return commentDtos;
    }

    public CommentDto readComment(UUID id){
        return commentRepository.getById(id).commentDto();
    }

    public CommentDto update(UUID commentId, CommentDto commentDto) {
        Comment comment = commentRepository.getById(commentId);
        comment.update(commentDto.getBody());

        return commentRepository.save(comment).commentDto();
    }

    public void deleteComment(UUID id){
        commentRepository.deleteById(id);
    }
}
