package com.jj.hello_blog.web.member.controller;

import com.jj.hello_blog.domain.member.entity.Member;
import com.jj.hello_blog.domain.member.service.MemberService;
import com.jj.hello_blog.web.member.form.SignInForm;
import com.jj.hello_blog.web.member.form.SignUpForm;
import com.jj.hello_blog.web.session.SessionConst;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    public void me() throws Exception {

        // Given
        Member member = new Member(1, "test@test.com", "123456");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.MEMBER_KEY, member);

        // When
        ResultActions resultActions = mockMvc.perform(get("/member/me").session(session));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(member.getEmail()));
    }

    @Test
    public void signIn() throws Exception {
        // Given
        Member member = new Member(1, "test@test.com", "123456");
        when(memberService.signIn(any(SignInForm.class))).thenReturn(member);

        MockHttpSession session = new MockHttpSession();

        // When
        ResultActions resultActions = mockMvc.perform(post("/member/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test@test.com\", \"password\": \"123456\"}")
                .session(session));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(member.getEmail()));

        Member sessionMember = (Member) session.getAttribute(SessionConst.MEMBER_KEY);
        assertEquals(sessionMember.getEmail(), member.getEmail());
        assertEquals(sessionMember.getPassword(), member.getPassword());
    }

    @Test
    public void signUp() throws Exception {
        // Given
        Member member = new Member(1, "test@test.com", "123456");
        when(memberService.signUp(any(SignUpForm.class))).thenReturn(member);

        MockHttpSession session = new MockHttpSession();

        // When
        ResultActions resultActions = mockMvc.perform(post("/member/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test@test.com\", \"password\": \"123456\"}")
                .session(session));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(member.getEmail()));

        Member sessionMember = (Member) session.getAttribute(SessionConst.MEMBER_KEY);
        assertEquals(sessionMember.getEmail(), member.getEmail());
        assertEquals(sessionMember.getPassword(), member.getPassword());
    }

    @Test
    public void checkDuplicatedEmail() throws Exception {
        // Given
        when(memberService.checkDuplicatedEmail("test@test.com")).thenReturn(true);

        // When
        ResultActions resultActions = mockMvc.perform(get("/member/test@test.com"));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}