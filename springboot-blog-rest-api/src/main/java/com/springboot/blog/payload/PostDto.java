package com.springboot.blog.payload;

import com.springboot.blog.entity.Comments;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {
    private long id;
    @NotEmpty
    @Size(min = 2, message = "Title should contain minimum 2 characters")
    private String title;
    @NotEmpty
    @Size(min=10, message = "Description should contain minimum 10 characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentsDto> comments;
}
