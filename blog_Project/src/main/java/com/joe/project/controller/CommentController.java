package com.joe.project.controller;

import com.joe.project.dto.CommentDto;
import com.joe.project.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/articles/{articleId}")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public List<CommentDto> getComments(){
        return commentService.readComments();
    }

    @PostMapping("/comment")
    public void postComment(@RequestBody CommentDto commentDto){
        commentService.createComment(commentDto);
    }

//    @PutMapping(path = "/article/{articleId}/comments/{commentId}")
//    public void putComment(@PathVariable("commentId") Long id,
//                           @RequestBody CommentDto commentDto){

//    }

    @PutMapping("comments/{commentId}")
    public CommentDto update(@PathVariable("commentId") UUID commentId,
                             @Valid @RequestBody CommentDto commentDto) {
        return commentService.update(commentId, commentDto);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("commentId") UUID id){
        commentService.deleteComment(id);
    }

}
