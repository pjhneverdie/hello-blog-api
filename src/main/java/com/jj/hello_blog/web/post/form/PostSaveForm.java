package com.jj.hello_blog.web.post.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PostSaveForm {
    @Size(min = 1, max = 50)
    @NotBlank
    @NotNull
    private final String title;

    @NotBlank
    @NotNull
    private final String content;

    @NotNull
    private final int categoryId;

    public PostSaveForm(String title, String content, int categoryId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }
}
