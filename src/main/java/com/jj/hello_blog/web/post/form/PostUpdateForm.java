package com.jj.hello_blog.web.post.form;

import lombok.Getter;

@Getter
public class PostUpdateForm extends PostSaveForm {

    private final int id;

    public PostUpdateForm(int id, String title, String content, int categoryId) {
        super(title, content, categoryId);
        this.id = id;
    }

}
