package com.jj.hello_blog.web.category.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class CategoryAddForm {
    @NotBlank
    @NotNull
    private final String name;

    private final Integer parentId;

    public CategoryAddForm(String name, Integer parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}
