package com.jj.hello_blog.web.post;

import com.jj.hello_blog.common.aws.service.S3BucketService;
import com.jj.hello_blog.web.post.validation.FileTypeConstraint;

import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {
    private final S3BucketService s3BucketService;

    @PostMapping("/image")
    public String uploadImage(@NotNull @FileTypeConstraint MultipartFile file) {
        return s3BucketService.putS3Object(file);
    }
}
