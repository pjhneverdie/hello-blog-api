package com.jj.hello_blog.web.post.form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostUpdateForm extends PostSaveForm {

    @NotNull
    private final Integer id;

    public PostUpdateForm(Integer id, String title, String content, Integer categoryId) {
        super(title, content, categoryId);
        this.id = id;
    }

}
