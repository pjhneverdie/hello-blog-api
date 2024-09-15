package com.jj.hello_blog.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class PostSaveDto {

    private final String title;

    private final String content;

    private final MultipartFile thumbImageFile;

    private final int categoryId;

}
