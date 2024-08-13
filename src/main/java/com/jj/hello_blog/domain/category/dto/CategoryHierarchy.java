package com.jj.hello_blog.domain.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryHierarchy {

    private final int id;

    private final String thumbUrl;

    private final String name;

    private final Integer parentId;

}
