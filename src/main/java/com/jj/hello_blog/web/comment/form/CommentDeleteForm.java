package com.jj.hello_blog.web.comment.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentDeleteForm {

    private final int id;

    private final int memberId;

}
