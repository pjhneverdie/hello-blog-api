package com.jj.hello_blog.web.member.controller;

import com.jj.hello_blog.domain.common.exception.CustomException;
import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.member.dto.MemberResponse;
import com.jj.hello_blog.domain.member.dto.MemberSignInDto;
import com.jj.hello_blog.domain.member.dto.MemberSignUpDto;
import com.jj.hello_blog.domain.member.exception.MemberExceptionCode;
import com.jj.hello_blog.domain.member.service.MemberService;
import com.jj.hello_blog.web.ControllerTestBase;
import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.member.form.MemberSignInForm;
import com.jj.hello_blog.web.member.form.MemberSignUpForm;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        ApiResponse<MemberResponse> response = new ApiResponse<>(new MemberResponse(member.getEmail()));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
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
        ApiResponse<MemberResponse> response = new ApiResponse<>(new MemberResponse(member.getEmail()));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));

        checkSessionAttribute(session, member);
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
        ApiResponse<Void> response = new ApiResponse<>(null);
        response.setExceptionCode(MemberExceptionCode.SIGN_IN_FAILED.code());

        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void signUp() throws Exception {
        // Given
        MemberSignUpForm memberSignUpForm = new MemberSignUpForm("test@test.com", "123456");

        Member member = createMember(memberSignUpForm.getEmail(), memberSignUpForm.getPassword());

        when(memberService.signUp(any(MemberSignUpDto.class))).thenReturn(member);

        MockHttpSession session = new MockHttpSession();

        // When
        ResultActions resultActions = mockMvc.perform(post("/member/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(memberSignUpForm))
                .session(session));

        // Then
        ApiResponse<MemberResponse> response = new ApiResponse<>(new MemberResponse(member.getEmail()));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));

        checkSessionAttribute(session, member);
    }

    @Test
    @DisplayName("회원가입 실패 테스트")
    public void signUpWithDuplicatedEmail() throws Exception {
        // Given
        MemberSignUpForm memberSignUpForm = new MemberSignUpForm("test@test.com", "123456");

        doThrow(new CustomException(MemberExceptionCode.DUPLICATED_EMAIL))
                .when(memberService)
                .signUp(any(MemberSignUpDto.class));

        MockHttpSession session = new MockHttpSession();

        // When
        ResultActions resultActions = mockMvc.perform(post("/member/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(memberSignUpForm))
                .session(session));

        // Then
        ApiResponse<Void> response = new ApiResponse<>(null);
        response.setExceptionCode(MemberExceptionCode.DUPLICATED_EMAIL.code());

        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(toJson(response)));
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
        ApiResponse<Boolean> response = new ApiResponse<>(true);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    @DisplayName("회원탈퇴 테스트")
    public void deleteMember() throws Exception {
        // Given
        Member member = createMember("test@test.com", "123456");
        MockHttpSession session = createMockHttpSessionWithMember(member);

        when(memberService.deleteMember(any(int.class))).thenReturn(true);

        // When
        ResultActions resultActions = mockMvc.perform(delete("/member/" + member.getId().toString()).session(session));

        // Then
        ApiResponse<Boolean> response = new ApiResponse<>(true);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

    /**
     * createMember, 멤버 데이터 생성 유틸
     */
    private Member createMember(String email, String password) {
        return new Member(1, email, password);
    }

    /**
     * createMockHttpSessionWithMember, 멤버 세션 생성 유틸
     */
    private MockHttpSession createMockHttpSessionWithMember(Member member) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.MEMBER_KEY, member);
        return session;
    }

    /**
     * getMockHttpSessionWithMember, 멤버 세션 검증 유틸
     */
    private void checkSessionAttribute(MockHttpSession session, Member member) {
        Member sessionMember = (Member) session.getAttribute(SessionConst.MEMBER_KEY);
        assert sessionMember != null;
        assertEquals(sessionMember.getEmail(), member.getEmail());
        assertEquals(sessionMember.getPassword(), member.getPassword());
    }

}