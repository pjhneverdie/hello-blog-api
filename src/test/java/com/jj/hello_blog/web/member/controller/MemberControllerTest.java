package com.jj.hello_blog.web.member.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.member.dto.MemberResponse;
import com.jj.hello_blog.domain.member.dto.MemberSignInDto;

import com.jj.hello_blog.domain.member.service.MemberService;
import com.jj.hello_blog.domain.member.exception.MemberExceptionCode;

import com.jj.hello_blog.domain.common.exception.CustomException;

import com.jj.hello_blog.web.ControllerTestBase;

import com.jj.hello_blog.web.common.session.SessionConst;
import com.jj.hello_blog.web.common.response.ApiResponse;

import com.jj.hello_blog.web.member.form.MemberSignInForm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends ControllerTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("멤버 정보 조회 테스트")
    public void me() throws Exception {
        // Given
        Member member = createMember("test@test.com", "123456");
        MockHttpSession session = createMockHttpSessionWithMember(member);

        // When
        ResultActions resultActions = mockMvc.perform(get("/member/me").session(session));

        // Then
        ApiResponse<MemberResponse> apiResponse = new ApiResponse<>(new MemberResponse(member.getEmail()));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }

    @Test
    @DisplayName("로그인 테스트")
    public void signIn() throws Exception {
        // Given
        MemberSignInForm memberSignInForm = new MemberSignInForm("test@test.com", "123456");
        Member member = createMember(memberSignInForm.getEmail(), memberSignInForm.getPassword());

        when(memberService.signIn(any(MemberSignInDto.class))).thenReturn(member);

        MockHttpSession session = new MockHttpSession();

        // When
        ResultActions resultActions = mockMvc.perform(post("/member/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(memberSignInForm))
                .session(session));

        // Then
        ApiResponse<MemberResponse> apiResponse = new ApiResponse<>(new MemberResponse(member.getEmail()));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));

        validateMemberInSessionAttribute(session, member);
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void signInWithWrongMember() throws Exception {
        // Given
        MemberSignInForm memberSignInForm = new MemberSignInForm("test@test.com", "123456");

        doThrow(new CustomException(MemberExceptionCode.SIGN_IN_FAILED))
                .when(memberService)
                .signIn(any(MemberSignInDto.class));

        MockHttpSession session = new MockHttpSession();

        // When
        ResultActions resultActions = mockMvc.perform(post("/member/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(memberSignInForm))
                .session(session));

        // Then
        ApiResponse<Void> apiResponse = new ApiResponse<>(null);
        apiResponse.setExceptionCode(MemberExceptionCode.SIGN_IN_FAILED.code());

        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(toJson(apiResponse)));
    }

    @Test
    @DisplayName("로그아웃 테스트")
    public void signOut() throws Exception {
        // Given
        Member member = createMember("test@test.com", "123456");
        MockHttpSession session = createMockHttpSessionWithMember(member);

        // When
        ResultActions resultActions = mockMvc.perform(get("/member/signOut").session(session));

        // Then
        ApiResponse<Void> response = new ApiResponse<>(null);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

    /**
     * validateMemberInSessionAttribute, 멤버 세션 검증 유틸
     */
    private void validateMemberInSessionAttribute(MockHttpSession session, Member member) {
        Member sessionMember = (Member) session.getAttribute(SessionConst.MEMBER_KEY);
        assert sessionMember != null;
        assertEquals(sessionMember.getEmail(), member.getEmail());
        assertEquals(sessionMember.getPassword(), member.getPassword());
    }

}