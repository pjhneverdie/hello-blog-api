package com.jj.hello_blog.domain.member.repository;

import com.jj.hello_blog.domain.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final MemberMapper memberMapper;

    public void insertMember(Member member) {
        memberMapper.insertMember(member);
    }

    public void deleteMemberById(int id) {
        memberMapper.deleteMemberById(id);
    }

    public Optional<Member> selectMemberByEmail(String email) {
        return Optional.ofNullable(memberMapper.selectMemberByEmail(email));
    }

}
