package com.jj.hello_blog.web.post.controller;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jj.hello_blog.domain.post.dto.PostResponse;
import com.jj.hello_blog.domain.post.service.PostService;
import com.jj.hello_blog.domain.post.dto.PostPaginationCond;

import com.jj.hello_blog.web.common.response.ApiResponse;

@Validated
@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<PostResponse>> getPost(@NotNull @PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(postService.getPost(id)));
    }

    @GetMapping("/recent")
    ResponseEntity<ApiResponse<List<PostResponse>>> getRecentPosts(@NotNull @RequestParam("page") Integer page) {
        PostPaginationCond postPaginationCond = new PostPaginationCond(page);

        return ResponseEntity.ok(new ApiResponse<>(postService.getRecentPosts(postPaginationCond)));
    }

    @GetMapping("/category/{categoryId}")
    ResponseEntity<ApiResponse<List<PostResponse>>> getPostsInCategory(@NotNull @PathVariable Integer categoryId, @NotNull @RequestParam("page") Integer page) {
        PostPaginationCond postPaginationCond = new PostPaginationCond(page);

        return ResponseEntity.ok(new ApiResponse<>(postService.getPostsInCategory(categoryId, postPaginationCond)));
    }

}
