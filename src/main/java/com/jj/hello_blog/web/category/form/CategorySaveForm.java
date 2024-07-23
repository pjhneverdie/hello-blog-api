package com.jj.hello_blog.web.category.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class CategorySaveForm {
    @NotBlank
    @NotNull
    private final String name;

    public CategorySaveForm(String name) {
        this.name = name;
    }
}
