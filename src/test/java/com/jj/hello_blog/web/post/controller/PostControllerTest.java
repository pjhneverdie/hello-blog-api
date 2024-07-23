package com.jj.hello_blog.web.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.hello_blog.common.aws.service.S3BucketService;
import com.jj.hello_blog.domain.post.dto.PostResponse;
import com.jj.hello_blog.domain.post.dto.PostSaveDto;
import com.jj.hello_blog.domain.post.dto.PostUpdateDto;
import com.jj.hello_blog.domain.post.service.PostService;
import com.jj.hello_blog.web.post.form.PostSaveForm;

import com.jj.hello_blog.web.post.form.PostUpdateForm;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private S3BucketService s3BucketService;

    @MockBean
    private PostService postService;

    @Test
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
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string(dummyURL));
    }

    @Test
    public void post() throws Exception {
        // Given
        PostSaveForm postSaveForm = getPostSaveForm();
        PostResponse post = getPostResponse(postSaveForm);

        when(postService.save(any(PostSaveDto.class))).thenReturn(post);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/post/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getPostSaveFormJson(postSaveForm)));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()));
    }

    @Test
    public void update() throws Exception {
        // Given
        PostUpdateForm postUpdateForm = getPostUpdateForm();
        PostResponse postResponse = getPostResponse(postUpdateForm);

        when(postService.updatePost(any(PostUpdateDto.class))).thenReturn(postResponse);

        // When
        ResultActions resultActions = mockMvc.perform(patch("/post/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getPostUpdateFormJson(postUpdateForm)));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postResponse.getId()))
                .andExpect(jsonPath("$.title").value(postResponse.getTitle()))
                .andExpect(jsonPath("$.content").value(postResponse.getContent()));
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

    private String getPostSaveFormJson(PostSaveForm postSaveForm) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(postSaveForm);
    }

    ////
    private PostUpdateForm getPostUpdateForm() {
        return new PostUpdateForm(1, "test", "test", 1);
    }

    private PostResponse getPostResponse(PostUpdateForm postUpdateForm) {
        return new PostResponse(
                postUpdateForm.getId(),
                postUpdateForm.getTitle(),
                postUpdateForm.getContent(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                postUpdateForm.getCategoryId(),
                0,
                0);
    }

    private String getPostUpdateFormJson(PostUpdateForm postUpdateForm) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(postUpdateForm);
    }

}