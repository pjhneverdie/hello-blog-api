package com.jj.hello_blog.web.like.form;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public class LikeSaveForm {

    private final int memberId;

    private final int postId;

    private final Integer commentId;

}
