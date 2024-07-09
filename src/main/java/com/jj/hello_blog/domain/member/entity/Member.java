package com.jj.hello_blog.domain.member.entity;

import lombok.Getter;

@Getter
public class Member {
    private final Integer id;
    private final String email;
    private final String password;

    public Member(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
