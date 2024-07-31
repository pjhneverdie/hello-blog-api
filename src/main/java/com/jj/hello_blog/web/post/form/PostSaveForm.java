package com.jj.hello_blog.web.post.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostSaveForm {

    @Size(min = 1, max = 50)
    @NotBlank
    @NotNull
    private final String title;

    @NotBlank
    @NotNull
    private final String content;

    @NotNull
    private final Integer categoryId;

}
