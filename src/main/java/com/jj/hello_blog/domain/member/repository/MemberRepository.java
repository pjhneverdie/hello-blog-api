package com.jj.hello_blog.domain.member.repository;

import com.jj.hello_blog.domain.member.entity.Member;

public interface MemberRepository {
    Member signUp(Member member);
}
