package com.springboot.blog.services;

import com.springboot.blog.entity.Comments;
import com.springboot.blog.payload.CommentsDto;

import java.util.List;

public interface CommentService {
    CommentsDto createComment(long postId, CommentsDto commentDto);
    List<CommentsDto> getAllCommentsWithId(long postId);
    CommentsDto getCommentById(long postId, int commentId);
    CommentsDto updateCommentById(long postId, int commentId, CommentsDto commentRequest);
    void deleteCommentById(long postId, int commentId);
}
