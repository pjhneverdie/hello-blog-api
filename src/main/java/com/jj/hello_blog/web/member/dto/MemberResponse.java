package com.jj.hello_blog.web.member.dto;

import lombok.Getter;

@Getter
public class MemberResponse {
    private final String email;

    public MemberResponse(String email) {
        this.email = email;
    }
}
