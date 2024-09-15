package com.jj.hello_blog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;

import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.web.common.session.SessionConst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.jj.hello_blog.web.common.response.ApiResponse;

@Configurable
public class ControllerTestBase {

    @Autowired
    private ObjectMapper objectMapper;

    protected String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    protected <T> ApiResponse<T> createApiResponse(T value) {
        return new ApiResponse<>(value);
    }

    protected MockMultipartFile createMockFormFile(Object form, String fileName) throws JsonProcessingException {
        return new MockMultipartFile(fileName, fileName, "application/json", toJson(form).getBytes());
    }

    protected MockMultipartFile createMockImageFile(String fileName, String contentType) {
        return new MockMultipartFile(fileName, fileName + "." + contentType, "image" + "/" + contentType, "image".getBytes());
    }

    protected Member createMember(String email, String password) {
        return new Member(1, email, password);
    }

    protected MockHttpSession createMockHttpSessionWithMember(Member member) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.MEMBER_KEY, member);
        return session;
    }

}
