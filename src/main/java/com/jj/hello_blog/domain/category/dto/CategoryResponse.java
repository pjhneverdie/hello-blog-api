package com.jj.hello_blog.domain.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private int id;

    private String name;

    private String thumbUrl;

    private Integer parentId;

    private LocalDateTime createdAt;

    private int postCount;

}
