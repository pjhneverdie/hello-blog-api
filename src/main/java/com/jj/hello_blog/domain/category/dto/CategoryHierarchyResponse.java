package com.jj.hello_blog.domain.category.dto;

import java.util.List;
import java.util.ArrayList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryHierarchyResponse {

    private final int id;

    private final String name;

    private final String thumbUrl;

    private final List<CategoryHierarchyResponse> children = new ArrayList<>();

}
