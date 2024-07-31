package com.jj.hello_blog.domain.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikePostDto {
    private final int memberId;
    private final int postId;
}
