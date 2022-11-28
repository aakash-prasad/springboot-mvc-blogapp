package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentsDto;
import com.springboot.blog.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentsDto> createComment(@PathVariable int postId, @RequestBody CommentsDto commentsDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentsDto), HttpStatus.CREATED);
    }
    // Method to get all posts
    @GetMapping("/posts/{postId}/comments")
    public List<CommentsDto> getAllCommentsWithId(@PathVariable long postId){
        return commentService.getAllCommentsWithId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentsDto> getCommentById(@PathVariable(value = "postId")
                                                          long postId, @PathVariable(value="id") int commentId){
        CommentsDto commentsDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentsDto> updateCommentsById(@PathVariable(value = "postId") long postId,
                                                         @PathVariable(value = "id") int commentId,
                                                          @RequestBody   CommentsDto commentRequest) {
        CommentsDto updatedComment = commentService.updateCommentById(postId, commentId, commentRequest);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable int postId, @PathVariable(value = "id") int commentId){
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("Comment Deleted", HttpStatus.OK);
    }
}
