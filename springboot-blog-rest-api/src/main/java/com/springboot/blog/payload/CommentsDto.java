package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    private int id;
    @NotEmpty
    @Size(min = 2, message = "Minimum 2 character it should contain")
    private String body;
    @NotEmpty
    @Size(min = 2, message = "Minimum 2 character it should contain")
    private String name;
    @Email
    @NotNull
    private String email;
}
