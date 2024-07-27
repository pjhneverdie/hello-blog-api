package com.jj.hello_blog.domain.member.dto;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public class Member {

    private final Integer id;

    private final String email;

    private final String password;

}
