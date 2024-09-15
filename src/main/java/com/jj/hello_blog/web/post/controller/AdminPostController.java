package com.jj.hello_blog.web.post.controller;

import java.util.Map;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import jakarta.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.jj.hello_blog.domain.post.dto.PostSaveDto;
import com.jj.hello_blog.domain.post.dto.PostResponse;
import com.jj.hello_blog.domain.post.dto.PostUpdateDto;

import com.jj.hello_blog.domain.post.service.PostService;

import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.common.validation.FileTypeConstraint;

import com.jj.hello_blog.web.post.form.PostSaveForm;
import com.jj.hello_blog.web.post.form.PostUpdateForm;

@Validated
@RequestMapping("admin/post")
@RestController
@RequiredArgsConstructor
public class AdminPostController {

    private final PostService postService;

    @PostMapping("/image/temp")
    public ResponseEntity<ApiResponse<String>> uploadContentImageAsTemp(@Valid @NotNull @FileTypeConstraint MultipartFile contentImageFile) {
        return ResponseEntity.ok(new ApiResponse<>(postService.uploadContentImageAsTemp(contentImageFile)));
    }

    @PostMapping("/image/move")
    public ResponseEntity<ApiResponse<Map<String, String>>> moveContentImagesFromTempToImage(@Valid @NotNull @RequestBody Map<String, List<String>> params) {
        List<String> tempUrls = params.get("tempUrls");
        return ResponseEntity.ok(new ApiResponse<>(postService.moveContentImagesFromTempToImage(tempUrls)));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<PostResponse>> savePost(
            @Valid @FileTypeConstraint @RequestPart(required = false) MultipartFile thumbImageFile,
            @RequestPart PostSaveForm postSaveForm
    ) {
        int savedId = postService.savePost(new PostSaveDto(postSaveForm.getTitle(), postSaveForm.getContent(), thumbImageFile, postSaveForm.getCategoryId()));

        return ResponseEntity.ok(new ApiResponse<>(postService.getPost(savedId)));
    }

    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @Valid @FileTypeConstraint @RequestPart(required = false) MultipartFile thumbImageFile,
            @RequestPart PostUpdateForm postUpdateForm
    ) {
        PostUpdateDto postUpdateDto = new PostUpdateDto(postUpdateForm.getId(), postUpdateForm.getTitle(), postUpdateForm.getContent(), postUpdateForm.getThumbUrl(), thumbImageFile, postUpdateForm.getCategoryId());

        postService.updatePost(postUpdateDto);

        return ResponseEntity.ok(new ApiResponse<>(postService.getPost(postUpdateForm.getId())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@NotNull @PathVariable Integer id, @NotNull @RequestParam("relatedUrls") List<String> relatedUrls) {
        postService.deletePost(id, relatedUrls);

        return ResponseEntity.ok(new ApiResponse<>(null));
    }

}
