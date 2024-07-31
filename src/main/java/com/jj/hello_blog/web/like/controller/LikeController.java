package com.jj.hello_blog.web.like.controller;

import com.jj.hello_blog.domain.like.dto.LikeCommentDto;
import com.jj.hello_blog.domain.like.dto.LikePostDto;
import com.jj.hello_blog.domain.like.service.LikeService;
import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.like.form.LikeCommentForm;
import com.jj.hello_blog.web.like.form.LikePostForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/like")
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/post")
    ResponseEntity<ApiResponse<Integer>> like(@Valid @RequestBody LikePostForm likePostForm) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        likeService.likePost(
                                new LikePostDto(likePostForm.getMemberId(), likePostForm.getPostId())
                        ))
        );
    }

    @PostMapping("/comment")
    ResponseEntity<ApiResponse<Integer>> like(@Valid @RequestBody LikeCommentForm likeCommentForm) {
        return ResponseEntity.ok(
                new ApiResponse<>(
                        likeService.likeComment(
                                new LikeCommentDto(likeCommentForm.getMemberId(), likeCommentForm.getCommentId())
                        ))
        );
    }

}
