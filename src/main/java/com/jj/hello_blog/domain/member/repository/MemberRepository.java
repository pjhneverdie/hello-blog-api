package com.jj.hello_blog.domain.member.repository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.jj.hello_blog.domain.member.dto.Member;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final MemberMapper memberMapper;

    public void insertMember(Member member) {
        memberMapper.insertMember(member);
    }

    public Optional<Member> selectMemberByEmail(String email) {
        return Optional.ofNullable(memberMapper.selectMemberByEmail(email));
    }

}
