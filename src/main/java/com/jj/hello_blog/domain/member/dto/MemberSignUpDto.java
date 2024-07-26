package com.jj.hello_blog.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberSignUpDto extends MemberSignInDto{
    public MemberSignUpDto(String email, String password) {
        super(email, password);
    }
}
