package com.jj.hello_blog.web.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.hello_blog.domain.common.aws.service.S3BucketService;
import com.jj.hello_blog.domain.post.dto.PostResponse;
import com.jj.hello_blog.domain.post.dto.PostSaveDto;
import com.jj.hello_blog.domain.post.dto.PostUpdateDto;
import com.jj.hello_blog.domain.post.service.PostService;
import com.jj.hello_blog.web.ControllerTestBase;
import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.post.form.PostSaveForm;

import com.jj.hello_blog.web.post.form.PostUpdateForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest extends ControllerTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private S3BucketService s3BucketService;

    @MockBean
    private PostService postService;

    @Test
    @DisplayName("이미지 업로드 테스트")
    public void uploadImage() throws Exception {
        // Given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "testFile.jpg", "image/jpeg",
                new ByteArrayInputStream(("This is a test file").getBytes()));

        String dummyURL = "http://test/testFile.jpg";

        when(s3BucketService.putS3Object(any(MultipartFile.class))).thenReturn(dummyURL);

        // When
        ResultActions resultActions = mockMvc.perform(multipart("/post/image")
                .file(mockMultipartFile)
                .contentType(MediaType.MULTIPART_FORM_DATA));

        // Then
        ApiResponse<String> response = new ApiResponse<>(dummyURL);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    @DisplayName("게시글 작성 테스트")
    public void post() throws Exception {
        // Given
        PostSaveForm postSaveForm = getPostSaveForm();
        PostResponse postResponse = getPostResponse(postSaveForm);

        when(postService.savePost(any(PostSaveDto.class))).thenReturn(postResponse);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(postSaveForm)));

        // Then
        ApiResponse<PostResponse> response = new ApiResponse<>(postResponse);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updatePost() throws Exception {
        // Given
        PostUpdateForm postUpdateForm = getPostUpdateForm();

        when(postService.updatePost(any(PostUpdateDto.class))).thenReturn(true);

        // When
        ResultActions resultActions = mockMvc.perform(patch("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(postUpdateForm)));

        // Then
        ApiResponse<Boolean> response = new ApiResponse<>(true);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void deletePost() throws Exception {
        // Given
        when(postService.deletePost(any(int.class))).thenReturn(true);

        // When
        ResultActions resultActions = mockMvc.perform(delete("/post/1")
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        ApiResponse<Boolean> response = new ApiResponse<>(true);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }


    private PostSaveForm getPostSaveForm() {
        return new PostSaveForm("test", "test", 1);
    }

    private PostResponse getPostResponse(PostSaveForm postSaveForm) {
        return new PostResponse(
                1,
                postSaveForm.getTitle(),
                postSaveForm.getContent(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                postSaveForm.getCategoryId(),
                0,
                0);
    }

    private PostUpdateForm getPostUpdateForm() {
        return new PostUpdateForm(1, "test", "test", 1);
    }

}