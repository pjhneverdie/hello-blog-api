package com.jj.hello_blog.web.like.controller;

import com.jj.hello_blog.domain.like.dto.LikeCommentDto;
import com.jj.hello_blog.domain.like.dto.LikePostDto;
import com.jj.hello_blog.domain.like.service.LikeService;
import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.web.ControllerTestBase;
import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.common.session.SessionConst;
import com.jj.hello_blog.web.like.form.LikeCommentForm;
import com.jj.hello_blog.web.like.form.LikePostForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LikeController.class)
class LikeControllerTest extends ControllerTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikeService likeService;

    @Test
    @DisplayName("게시글 좋아요 테스트")
    void likePost() throws Exception {
        // Given
        when(likeService.likePost(any(LikePostDto.class))).thenReturn(1);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.MEMBER_KEY, new Member(1, "test@test.com", "123456"));

        // When
        ResultActions resultActions = mockMvc.perform(post("/like/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new LikePostForm(1, 1)))
                .session(session));

        // Then
        ApiResponse<Integer> response = new ApiResponse<>(1);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    @DisplayName("댓글 좋아요 테스트")
    void likeComment() throws Exception {
        // Given
        when(likeService.likeComment(any(LikeCommentDto.class))).thenReturn(1);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.MEMBER_KEY, new Member(1, "test@test.com", "123456"));

        // When
        ResultActions resultActions = mockMvc.perform(post("/like/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new LikeCommentForm(1, 1)))
                .session(session));

        // Then
        ApiResponse<Integer> response = new ApiResponse<>(1);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

}