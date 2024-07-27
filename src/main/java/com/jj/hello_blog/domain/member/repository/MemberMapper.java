package com.jj.hello_blog.domain.member.repository;

import com.jj.hello_blog.domain.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void saveMember(Member member);

    void deleteMember(int id);

    Member findMemberByEmail(String email);

}
