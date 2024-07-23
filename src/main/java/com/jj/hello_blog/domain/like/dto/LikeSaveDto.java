package com.jj.hello_blog.domain.like.dto;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public class LikeSaveDto {
    private final int memberId;

    private final int postId;

    private final Integer commentId;
}
