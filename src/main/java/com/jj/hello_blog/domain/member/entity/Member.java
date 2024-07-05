package com.jj.hello_blog.domain.member.entity;

import lombok.Getter;

@Getter
public class Member {
    private final Integer id;
    private final String name;
    private final String email;

    public Member(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
