package com.jj.hello_blog.domain.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private Integer id;

    private String thumbUrl;

    private String name;

    private Integer postCount;

}
