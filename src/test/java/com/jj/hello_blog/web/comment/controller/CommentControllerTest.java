package com.jj.hello_blog.web.comment.controller;

import com.jj.hello_blog.domain.comment.dto.CommentResponse;
import com.jj.hello_blog.domain.comment.dto.CommentSaveDto;
import com.jj.hello_blog.domain.comment.dto.CommentUpdateDto;
import com.jj.hello_blog.domain.comment.service.CommentService;
import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.web.ControllerTestBase;
import com.jj.hello_blog.web.comment.form.CommentSaveForm;
import com.jj.hello_blog.web.comment.form.CommentUpdateForm;
import com.jj.hello_blog.web.common.session.SessionConst;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest extends ControllerTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("댓글 작성 테스트")
    void saveComment() throws Exception {
        // Given
        // 폼
        CommentSaveForm commentSaveForm = new CommentSaveForm("test", 1, 1, 1);

        // 서비스 응답
        CommentResponse commentResponse = getCommentResponse(commentSaveForm);
        when(commentService.saveComment(any(CommentSaveDto.class))).thenReturn(commentResponse);

        // 세션
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.MEMBER_KEY, new Member(1, "test@test.com", "123456"));

        // When
        ResultActions resultActions = mockMvc.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(commentSaveForm))
                .session(session));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(commentResponse)));
    }

    @Test
    @DisplayName("댓글 작성 테스트")
    void updateComment() throws Exception {
        // Given
        // 폼
        CommentUpdateForm commentUpdateForm = new CommentUpdateForm(1, "test");

        // 서비스 응답
        when(commentService.updateComment(any(CommentUpdateDto.class))).thenReturn(true);

        // When
        ResultActions resultActions = mockMvc.perform(patch("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(commentUpdateForm)));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void deleteComment() throws Exception {
        // Given
        // 서비스 응답
        when(commentService.deleteComment(any(int.class))).thenReturn(true);

        // When
        ResultActions resultActions = mockMvc.perform(delete("/comment/1"));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    private CommentResponse getCommentResponse(CommentSaveForm commentSaveForm) {
        return new CommentResponse(
                1,
                commentSaveForm.getContent(),
                LocalDateTime.now(),
                commentSaveForm.getMemberId(),
                commentSaveForm.getPostId(),
                commentSaveForm.getParentId(),
                "test@test.com",
                0
        );
    }

}