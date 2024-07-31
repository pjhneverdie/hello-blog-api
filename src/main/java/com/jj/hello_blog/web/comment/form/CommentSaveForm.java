package com.jj.hello_blog.web.comment.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentSaveForm {

    @NotBlank
    @NotNull
    private final String content;

    @NotNull
    private final Integer memberId;

    @NotNull
    private final Integer postId;

    private final Integer parentId;

}
