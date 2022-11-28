package com.springboot.blog.services.impl;

import com.springboot.blog.entity.Comments;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentsDto;
import com.springboot.blog.repository.CommentsRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceimpl implements CommentService {
    private CommentsRepository commentsRepository;
    private PostRepository postRepository;
    public CommentServiceimpl(CommentsRepository commentsRepository ,PostRepository postRepository){
        this.commentsRepository=commentsRepository;
        this.postRepository=postRepository;
    }
    @Override
    public CommentsDto createComment(long postId, CommentsDto commentDto) {
        Comments comments = mapToEntity(commentDto);
        //retrieve the post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));

        //set post to comment entity
        comments.setPost(post);
        //comment entity to dB
        Comments newComments = commentsRepository.save(comments);
        return mapToDto(newComments);
    }

    @Override
    public List<CommentsDto> getAllCommentsWithId(long postId) {
        List<Comments> comments = commentsRepository.findByPostId(postId);

        return comments.stream().map(comment-> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentsDto getCommentById(long postId, int commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));
        Comments comment = commentsRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId()!=(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentsDto updateCommentById(long postId, int commentId, CommentsDto commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));
        Comments comment = commentsRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId()!=(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        comment.setName(commentRequest.getName());
        comment.setBody(commentRequest.getBody());
        comment.setEmail(commentRequest.getEmail());

        Comments updatedComment = commentsRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, int commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",postId));
        Comments comment = commentsRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId()!=(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        commentsRepository.delete(comment);
    }

    public CommentsDto mapToDto(Comments comments){
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setId(comments.getId());
        commentsDto.setBody(comments.getBody());
        commentsDto.setName(comments.getName());
        commentsDto.setEmail(comments.getEmail());
        return commentsDto;
    }

    public Comments mapToEntity(CommentsDto commentsDto){
        Comments comments = new Comments();
        comments.setId(commentsDto.getId());
        comments.setName(commentsDto.getName());
        comments.setEmail(commentsDto.getEmail());
        comments.setBody(commentsDto.getBody());
        return comments;
    }
}
