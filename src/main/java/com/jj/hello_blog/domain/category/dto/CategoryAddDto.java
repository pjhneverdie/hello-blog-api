package com.jj.hello_blog.domain.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class CategoryAddDto {

    private final String name;

    private final MultipartFile thumbImageFile;

    private final Integer parentId;

}
