package com.jj.hello_blog.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentSaveDto {
    private final String content;
    private final int member_id;
    private final int postId;
    private final Integer parentId;
    private final String email;
}
