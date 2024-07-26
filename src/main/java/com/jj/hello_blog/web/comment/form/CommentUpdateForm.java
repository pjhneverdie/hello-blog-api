package com.jj.hello_blog.web.comment.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUpdateForm {

    private final int id;

    @NotBlank
    @NotNull
    private final String content;

}
