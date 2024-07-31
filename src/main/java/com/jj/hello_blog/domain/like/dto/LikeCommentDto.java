package com.jj.hello_blog.domain.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeCommentDto {
    private final int memberId;
    private final int commentId;
}
