package com.jj.hello_blog.web.member.controller;

import com.jj.hello_blog.domain.member.entity.Member;
import com.jj.hello_blog.domain.member.service.MemberService;
import com.jj.hello_blog.web.member.form.MemberForm;
import com.jj.hello_blog.web.session.SessionConst;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    public void signUp() throws Exception {
        // Given
        Member member = new Member(1,"John", "john@example.com");
        when(memberService.signUp(any(MemberForm.class))).thenReturn(member);

        // When
        ResultActions resultActions = mockMvc.perform(post("/member/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John\", \"email\": \"john@example.com\"}"));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(request().sessionAttribute(SessionConst.MEMBER_KEY, member));
    }
}