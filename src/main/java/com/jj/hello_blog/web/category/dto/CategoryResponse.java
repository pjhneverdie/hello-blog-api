package com.jj.hello_blog.web.category.dto;

import com.jj.hello_blog.domain.category.entity.mybatis.Category;

import lombok.Getter;

import java.util.List;
import java.util.ArrayList;

@Getter
public class CategoryResponse {
    private final int id;
    private final String name;
    private final List<Category> children = new ArrayList<>();

    public CategoryResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
