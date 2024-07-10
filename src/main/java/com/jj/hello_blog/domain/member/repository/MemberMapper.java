package com.jj.hello_blog.domain.member.repository;

import com.jj.hello_blog.domain.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    Member signIn(String email, String password);

    void signUp(Member member);

    Member findByEmail(String email);
}
