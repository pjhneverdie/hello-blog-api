package com.jj.hello_blog.web.category.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class CategoryAddForm {

    @NotBlank
    @NotNull
    private final String name;

    private final Integer parentId;

}
