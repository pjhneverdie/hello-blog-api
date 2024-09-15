package com.jj.hello_blog.web.post.controller;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.jj.hello_blog.domain.post.dto.PostSaveDto;
import com.jj.hello_blog.domain.post.dto.PostResponse;
import com.jj.hello_blog.domain.post.dto.PostUpdateDto;
import com.jj.hello_blog.domain.post.service.PostService;

import com.jj.hello_blog.web.ControllerTestBase;
import com.jj.hello_blog.web.post.form.PostSaveForm;
import com.jj.hello_blog.web.post.form.PostUpdateForm;
import com.jj.hello_blog.web.common.response.ApiResponse;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(AdminPostController.class)
class AdminPostControllerTest extends ControllerTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private static final String BASE_URL = "/admin/post";

    @Test
    @DisplayName("게시글 본문에 이미지 삽입 테스트")
    void uploadContentImageAsTemp() throws Exception {
        // Given
        MockMultipartFile contentImageFile = createMockImageFile("contentImageFile", "png");

        when(postService.uploadContentImageAsTemp(any(MultipartFile.class))).thenReturn("tempUrl");

        // When
        ResultActions resultActions = mockMvc.perform(multipart(BASE_URL + "/image/temp")
                .file(contentImageFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .session(createMockHttpSessionWithMember(createMember("test@test.com", "123456"))));

        // Then
        ApiResponse<String> apiResponse = createApiResponse("tempUrl");

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }

    @Test
    @DisplayName("temp 폴더의 이미지를 image 폴더로 전환 테스트")
    void moveContentImagesFromTempToImage() throws Exception {
        // Given
        List<String> tempUrls = new ArrayList<>();
        tempUrls.add("url");

        Map<String, Object> param = new HashMap<>();
        param.put("tempUrls", tempUrls);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("url", "moved");

        when(postService.moveContentImagesFromTempToImage(any(List.class))).thenReturn(resultMap);

        // When
        ResultActions resultActions = mockMvc.perform(post(BASE_URL + "/image/move")
                .content(toJson(param))
                .contentType(MediaType.APPLICATION_JSON)
                .session(createMockHttpSessionWithMember(createMember("test@test.com", "123456"))));

        // Then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글 작성 테스트")
    void savePost() throws Exception {
        // Given
        PostSaveForm postSaveForm = new PostSaveForm("test", "test", 1);

        MockMultipartFile formFile = createMockFormFile(postSaveForm, "postSaveForm");
        MockMultipartFile thumbImageFile = createMockImageFile("thumbImageFile", "png");

        PostResponse postResponse = new PostResponse(1, postSaveForm.getTitle(), postSaveForm.getContent(), "url", postSaveForm.getCategoryId(), LocalDateTime.now(), LocalDateTime.now());

        when(postService.savePost(any(PostSaveDto.class))).thenReturn(postResponse.getId());
        when(postService.getPost(any(int.class))).thenReturn(postResponse);

        // When
        ResultActions resultActions = mockMvc.perform(multipart(BASE_URL)
                .file(formFile)
                .file(thumbImageFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .session(createMockHttpSessionWithMember(createMember("test@test.com", "123456"))));

        // Then
        ApiResponse<PostResponse> apiResponse = createApiResponse(postResponse);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updatePost() throws Exception {
        // Given
        PostUpdateForm postUpdateForm = new PostUpdateForm(1, "test", "test", "url", 1);

        MockMultipartFile formFile = createMockFormFile(postUpdateForm, "postUpdateForm");
        MockMultipartFile thumbImageFile = createMockImageFile("thumbImageFile", "png");

        PostResponse postResponse = new PostResponse(1, postUpdateForm.getTitle(), postUpdateForm.getContent(), postUpdateForm.getThumbUrl(), postUpdateForm.getCategoryId(), LocalDateTime.now(), LocalDateTime.now());

        doNothing().when(postService).updatePost(any(PostUpdateDto.class));
        when(postService.getPost(any(int.class))).thenReturn(postResponse);

        // When
        ResultActions resultActions = mockMvc.perform(multipart(HttpMethod.PATCH, BASE_URL)
                .file(formFile)
                .file(thumbImageFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .session(createMockHttpSessionWithMember(createMember("test@test.com", "123456"))));


        // Then
        ApiResponse<PostResponse> apiResponse = createApiResponse(postResponse);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deletePost() throws Exception {
        // Given
        doNothing().when(postService).deletePost(any(int.class), any(List.class));

        List<String> urls = new ArrayList<>();
        urls.add("https://hello-blog-api-dev-server-bucket.s3.ap-northeast-2.amazonaws.com/image/testFile1.jpg");
        urls.add("https://hello-blog-api-dev-server-bucket.s3.ap-northeast-2.amazonaws.com/image/testFile2.jpg");

        // When
        ResultActions resultActions = mockMvc.perform(delete(BASE_URL + "/1")
                .param("relatedUrls", urls.get(0))
                .param("relatedUrls", urls.get(1))
                .session(createMockHttpSessionWithMember(createMember("test@test.com", "123456"))));

        // Then
        ApiResponse<Void> apiResponse = createApiResponse(null);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }

}