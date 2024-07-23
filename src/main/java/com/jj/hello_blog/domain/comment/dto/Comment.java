package com.jj.hello_blog.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Comment {
    private final Integer id;
    private final String content;
    private final LocalDateTime createdAt;
    private final Integer memberId;
    private final int postId;
    private final Integer parentId;
}
