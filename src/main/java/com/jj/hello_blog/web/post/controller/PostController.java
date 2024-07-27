package com.jj.hello_blog.web.post.controller;

import com.jj.hello_blog.domain.common.aws.service.S3BucketService;
import com.jj.hello_blog.domain.post.dto.PostResponse;
import com.jj.hello_blog.domain.post.dto.PostSaveDto;
import com.jj.hello_blog.domain.post.dto.PostUpdateDto;
import com.jj.hello_blog.domain.post.service.PostService;
import com.jj.hello_blog.web.post.form.PostSaveForm;
import com.jj.hello_blog.web.post.form.PostUpdateForm;
import com.jj.hello_blog.web.post.validation.FileTypeConstraint;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final S3BucketService s3BucketService;

    private final PostService postService;

    @PostMapping("/image")
    public String uploadImage(@Valid @NotNull @FileTypeConstraint MultipartFile file) {
        return s3BucketService.putS3Object(file);
    }

    @PostMapping
    public PostResponse post(@Valid @RequestBody PostSaveForm postSaveForm) {
        return postService.savePost(new PostSaveDto(postSaveForm.getTitle(), postSaveForm.getContent(), postSaveForm.getCategoryId()));
    }

    @PatchMapping
    public boolean updatePost(@Valid @RequestBody PostUpdateForm postUpdateForm) {
        return postService.updatePost(
                new PostUpdateDto(
                        postUpdateForm.getId(), postUpdateForm.getTitle(),
                        postUpdateForm.getContent(), postUpdateForm.getCategoryId()
                )
        );
    }

    @DeleteMapping("/post/{id}")
    public boolean deletePostById(@PathVariable int id) {
        return postService.deletePostById(id);
    }

}
