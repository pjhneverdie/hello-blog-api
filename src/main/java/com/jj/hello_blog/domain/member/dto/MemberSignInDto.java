package com.jj.hello_blog.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberSignInDto {
    private final String email;
    private final String password;
}
