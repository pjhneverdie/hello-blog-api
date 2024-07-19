package com.jj.hello_blog.web.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.hello_blog.common.aws.service.S3BucketService;
import com.jj.hello_blog.domain.post.entity.Post;
import com.jj.hello_blog.domain.post.service.PostService;
import com.jj.hello_blog.web.post.form.PostSaveForm;

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
        Post postFromPostSaveForm = getPostFromPostSaveForm(postSaveForm);

        when(postService.post(any(PostSaveForm.class))).thenReturn(postFromPostSaveForm);

        // When
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/post/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getPostSaveFormJson(postSaveForm)));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(postFromPostSaveForm.getTitle()))
                .andExpect(jsonPath("$.content").value(postFromPostSaveForm.getContent()));
    }

    private PostSaveForm getPostSaveForm() {
        return new PostSaveForm("test", "test", 1);
    }

    private String getPostSaveFormJson(PostSaveForm postSaveForm) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(postSaveForm);
    }

    private Post getPostFromPostSaveForm(PostSaveForm postSaveForm) {
        return new Post(1, postSaveForm.getTitle(), postSaveForm.getContent(), LocalDateTime.now(), LocalDateTime.now(), postSaveForm.getCategoryId());
    }
}