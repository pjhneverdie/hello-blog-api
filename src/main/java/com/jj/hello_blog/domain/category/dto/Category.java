package com.jj.hello_blog.domain.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Category {

    private final Integer id;

    private final String name;

    private final String thumbUrl;

    private final Integer parentId;

    private final LocalDateTime createdAt;

}
