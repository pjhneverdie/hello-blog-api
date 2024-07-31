package com.jj.hello_blog.web.like.form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikeCommentForm {

    @NotNull
    private final Integer memberId;

    @NotNull
    private final Integer commentId;

}
