package com.jj.hello_blog.domain.category.dto;

import com.jj.hello_blog.domain.post.dto.PostResponse;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private int id;
    private String name;
    private List<PostResponse> posts;
}
