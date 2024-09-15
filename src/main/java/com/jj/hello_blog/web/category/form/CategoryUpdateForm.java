package com.jj.hello_blog.web.category.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.jj.hello_blog.web.category.form.validation.CategoryHierarchyConstraint;

@Getter
@RequiredArgsConstructor
@CategoryHierarchyConstraint
public class CategoryUpdateForm {

    @NotNull
    private final Integer id;

    @NotBlank
    @NotNull
    private final String name;

    @NotBlank
    @NotNull
    private final String thumbUrl;

    private final Integer parentId;

}
