package com.jj.hello_blog.domain.member.repository;

import com.jj.hello_blog.domain.member.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {
    void signUp(Member member);

    Member findByEmail(String email);
}
