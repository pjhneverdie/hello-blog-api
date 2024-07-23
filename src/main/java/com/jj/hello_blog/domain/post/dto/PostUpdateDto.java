package com.jj.hello_blog.domain.post.dto;

import lombok.Getter;

@Getter
public class PostUpdateDto extends PostSaveDto {
    private final int id;

    public PostUpdateDto(int id, String title, String content, int categoryId) {
        super(title, content, categoryId);
        this.id = id;
    }

}
