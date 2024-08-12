package com.jj.hello_blog.domain.member.repository;

import com.jj.hello_blog.domain.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void insertMember(Member member);

    void deleteMemberById(int id);

    Member selectMemberByEmail(String email);

}
