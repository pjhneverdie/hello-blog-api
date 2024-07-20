package com.jj.hello_blog.web.like.form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LikeLikeForm {

    @NotNull
    private final int memberId;

    @NotNull
    private final int postId;

    public LikeLikeForm(int memberId, int postId) {
        this.memberId = memberId;
        this.postId = postId;
    }

}
