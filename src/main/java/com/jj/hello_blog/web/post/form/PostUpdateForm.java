package com.jj.hello_blog.web.post.form;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUpdateForm {

    @NotNull
    private final Integer id;

    @Size(min = 1, max = 50)
    @NotBlank
    @NotNull
    private final String title;

    @NotBlank
    @NotNull
    private final String content;

    @NotBlank
    @NotNull
    private final String thumbUrl;

    @NotNull
    private final Integer categoryId;

}
