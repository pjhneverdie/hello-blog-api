package com.jj.hello_blog.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentUpdateDto {
    private final int id;
    private final String content;
}
