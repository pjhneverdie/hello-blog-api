package com.jj.hello_blog.web.comment.controller;

import com.jj.hello_blog.domain.comment.dto.CommentDeleteDto;
import com.jj.hello_blog.domain.comment.dto.CommentResponse;
import com.jj.hello_blog.domain.comment.dto.CommentSaveDto;
import com.jj.hello_blog.domain.comment.dto.CommentUpdateDto;
import com.jj.hello_blog.domain.comment.service.CommentService;
import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.web.comment.form.CommentDeleteForm;
import com.jj.hello_blog.web.comment.form.CommentSaveForm;
import com.jj.hello_blog.web.comment.form.CommentUpdateForm;
import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.common.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    ResponseEntity<ApiResponse<CommentResponse>> saveComment(HttpSession session, @Valid @RequestBody CommentSaveForm commentSaveForm) {
        Member member = (Member) session.getAttribute(SessionConst.MEMBER_KEY);

        return ResponseEntity.ok(
                new ApiResponse<>(commentService.saveComment(
                        new CommentSaveDto(
                                commentSaveForm.getContent(),
                                commentSaveForm.getMemberId(),
                                commentSaveForm.getPostId(),
                                commentSaveForm.getParentId(),
                                member.getEmail())
                )
                ));
    }

    @PatchMapping
    ResponseEntity<ApiResponse<Boolean>> updateComment(HttpSession httpSession, @Valid @RequestBody CommentUpdateForm commentUpdateForm) {
        Member member = (Member) httpSession.getAttribute(SessionConst.MEMBER_KEY);

        return ResponseEntity.ok(
                new ApiResponse<>(commentService.updateComment(
                        new CommentUpdateDto(
                                commentUpdateForm.getId(),
                                commentUpdateForm.getContent(),
                                member.getId())
                )
                ));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Boolean>> deleteComment(HttpSession httpSession, @Valid @NotNull @PathVariable int id) {
        Member member = (Member) httpSession.getAttribute(SessionConst.MEMBER_KEY);

        return ResponseEntity.ok(
                new ApiResponse<>(commentService.deleteComment(
                        new CommentDeleteDto(
                                id,
                                member.getId())
                )
                ));
    }

}
