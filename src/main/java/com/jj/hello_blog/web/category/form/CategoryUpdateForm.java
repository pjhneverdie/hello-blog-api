package com.jj.hello_blog.web.category.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryUpdateForm {

    private final int id;

    @NotBlank
    @NotNull
    private final String name;

}
