package com.jj.hello_blog.domain.member.repository;

import com.jj.hello_blog.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final MemberMapper memberMapper;

    public Member signUp(Member member) {
        memberMapper.signUp(member);
        return member;
    }

}
