package com.jj.hello_blog.domain.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class CategoryUpdateDto {

    private final int id;

    private final String name;

    private final String thumbUrl;

    private final MultipartFile thumbImageFile;

    private final Integer parentId;

}
