package com.jj.hello_blog.domain.category.entity.mybatis;

import lombok.Getter;

@Getter
public class Category {
    private final Integer id;
    private final String name;
    private final Integer parentId;

    public Category(Integer id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
}
