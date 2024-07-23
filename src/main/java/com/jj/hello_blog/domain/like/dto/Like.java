package com.jj.hello_blog.domain.like.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Like {

    private final Integer id;

    private final Integer memberId;

    private final int postId;

    private final Integer commentId;

}

