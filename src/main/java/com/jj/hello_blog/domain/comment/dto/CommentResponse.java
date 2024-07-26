package com.jj.hello_blog.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private int id;

    private String content;

    private LocalDateTime createdAt;

    private Integer memberId;

    private int postId;

    private Integer parentId;

    private String email;

    private int likeCount;

}
