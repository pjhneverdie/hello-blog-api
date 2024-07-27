package com.jj.hello_blog.domain.member.repository;

import com.jj.hello_blog.domain.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final MemberMapper memberMapper;

    public void saveMember(Member member) {
        memberMapper.saveMember(member);
    }

    public void deleteMember(int id) {
        memberMapper.deleteMember(id);
    }

    public Optional<Member> findMemberByEmail(String email) {
        return Optional.ofNullable(memberMapper.findMemberByEmail(email));
    }

}
