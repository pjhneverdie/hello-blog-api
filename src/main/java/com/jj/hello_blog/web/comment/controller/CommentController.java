package com.jj.hello_blog.web.comment.controller;

import com.jj.hello_blog.domain.comment.dto.CommentResponse;
import com.jj.hello_blog.domain.comment.dto.CommentSaveDto;
import com.jj.hello_blog.domain.comment.dto.CommentUpdateDto;
import com.jj.hello_blog.domain.comment.service.CommentService;
import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.web.comment.form.CommentSaveForm;
import com.jj.hello_blog.web.comment.form.CommentUpdateForm;
import com.jj.hello_blog.web.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    CommentResponse saveComment(HttpSession session, @Valid @RequestBody CommentSaveForm commentSaveForm) {
        Member member = (Member) session.getAttribute(SessionConst.MEMBER_KEY);

        return commentService.saveComment(
                new CommentSaveDto(
                        commentSaveForm.getContent(), commentSaveForm.getMemberId(),
                        commentSaveForm.getPostId(), commentSaveForm.getParentId(), member.getEmail())
        );
    }

    @PatchMapping
    boolean updateComment(@Valid @RequestBody CommentUpdateForm commentUpdateForm) {
        return commentService.updateComment(new CommentUpdateDto(commentUpdateForm.getId(), commentUpdateForm.getContent()));
    }

    @DeleteMapping("/{id}")
    boolean deleteComment(@PathVariable int id) {
        return commentService.deleteComment(id);
    }

}
