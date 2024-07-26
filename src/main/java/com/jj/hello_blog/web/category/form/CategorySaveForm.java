package com.jj.hello_blog.web.category.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter
public class CategorySaveForm {

    @NotBlank
    @NotNull
    private final String name;

    @JsonCreator
    public CategorySaveForm(String name) {
        this.name = name;
    }
}
