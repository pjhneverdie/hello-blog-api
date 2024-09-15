package com.jj.hello_blog.domain.member.repository;

import org.apache.ibatis.annotations.Mapper;

import com.jj.hello_blog.domain.member.dto.Member;

@Mapper
public interface MemberMapper {

    void insertMember(Member member);

    Member selectMemberByEmail(String email);

}
