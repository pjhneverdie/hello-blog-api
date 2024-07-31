package com.jj.hello_blog.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentDeleteDto {
    private final Integer id;
    private final Integer memberId;
}
