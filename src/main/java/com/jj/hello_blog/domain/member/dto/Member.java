package com.jj.hello_blog.domain.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Member {

    private final Integer id;

    private final String email;

    private final String password;

}
