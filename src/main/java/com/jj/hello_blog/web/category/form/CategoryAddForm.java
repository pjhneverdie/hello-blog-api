package com.jj.hello_blog.web.category.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class CategoryAddForm {

    @NotBlank
    @NotNull
    private final String name;

    private final Integer parentId;

}
