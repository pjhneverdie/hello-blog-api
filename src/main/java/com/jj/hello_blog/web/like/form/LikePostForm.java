package com.jj.hello_blog.web.like.form;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public class LikePostForm {

    @NotNull
    private final Integer memberId;

    @NotNull
    private final Integer postId;


}
