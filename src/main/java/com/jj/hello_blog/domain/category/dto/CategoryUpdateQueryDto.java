package com.jj.hello_blog.domain.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryUpdateQueryDto {

    private final int id;

    private final String name;

    private final String thumbUrl;

    private final Integer parentId;

}
