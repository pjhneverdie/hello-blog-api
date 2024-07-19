package com.jj.hello_blog.web.post.controller;

import com.jj.hello_blog.common.aws.service.S3BucketService;
import com.jj.hello_blog.domain.post.entity.Post;
import com.jj.hello_blog.domain.post.service.PostService;
import com.jj.hello_blog.web.post.form.PostSaveForm;
import com.jj.hello_blog.web.post.validation.FileTypeConstraint;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/post")
    public Post post(@Valid @RequestBody PostSaveForm postSaveForm) {
        return postService.post(postSaveForm);
    }
}
