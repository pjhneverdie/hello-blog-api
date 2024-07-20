package com.jj.hello_blog.domain.like.entity;

import lombok.Getter;

@Getter
public class Like {
    private final Integer id;
    private final int memberId;
    private final int postId;

    public Like(Integer id, int memberId, int postId) {
        this.id = id;
        this.memberId = memberId;
        this.postId = postId;
    }
}
