package com.jj.hello_blog.web.like.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.hello_blog.domain.like.dto.LikeSaveDto;
import com.jj.hello_blog.domain.like.service.LikeService;
import com.jj.hello_blog.web.like.form.LikeSaveForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikeService likeService;

    @Test
    void like() throws Exception {
        // Given
        String likeSaveFormJson = getLikeSaveFormJson(getLikeSaveForm());

        when(likeService.saveLike(any(LikeSaveDto.class))).thenReturn(1);

        // When
        ResultActions resultActions = mockMvc.perform(post("/like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(likeSaveFormJson));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    private LikeSaveForm getLikeSaveForm() {
        return new LikeSaveForm(1, 1, 1);
    }

    private String getLikeSaveFormJson(LikeSaveForm likeSaveForm) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(likeSaveForm);
    }

}