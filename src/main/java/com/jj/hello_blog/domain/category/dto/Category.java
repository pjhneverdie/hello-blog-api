package com.jj.hello_blog.domain.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Category {

    private final Integer id;

    private final String name;

}