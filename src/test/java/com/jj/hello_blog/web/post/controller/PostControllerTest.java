package com.jj.hello_blog.web.post.controller;

import java.util.List;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.jj.hello_blog.domain.post.service.PostService;

import com.jj.hello_blog.domain.post.dto.PostResponse;
import com.jj.hello_blog.domain.post.dto.PostPaginationCond;

import com.jj.hello_blog.web.ControllerTestBase;
import com.jj.hello_blog.web.common.response.ApiResponse;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(PostController.class)
class PostControllerTest extends ControllerTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private static final String BASE_URL = "/post";

    @Test
    @DisplayName("최근 게시글 페이지네이션 테스트")
    public void getRecentPosts() throws Exception {
        int page = 1;

        List<PostResponse> postResponses = List.of(new PostResponse(1, "title", "content", "thumbUrl", 1, LocalDateTime.now(), LocalDateTime.now()));

        when(postService.getRecentPosts(any(PostPaginationCond.class))).thenReturn(postResponses);

        ResultActions resultActions = mockMvc.perform(get(BASE_URL + "/recent")
                .param("page", String.valueOf(page)));

        ApiResponse<List<PostResponse>> apiResponse = new ApiResponse<>(postResponses);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }

    @Test
    @DisplayName("해당 카테고리의 게시글 페이지네이션 테스트")
    public void getPostsInCategory() throws Exception {
        int categoryId = 1;
        int page = 1;

        List<PostResponse> postResponses = List.of(new PostResponse(1, "title", "content", "thumbUrl", 1, LocalDateTime.now(), LocalDateTime.now()));

        when(postService.getPostsInCategory(any(int.class), any(PostPaginationCond.class))).thenReturn(postResponses);

        ResultActions resultActions = mockMvc.perform(get(BASE_URL + "/category/" + categoryId)
                .param("page", String.valueOf(page)));

        ApiResponse<List<PostResponse>> apiResponse = new ApiResponse<>(postResponses);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }

}