package com.jj.hello_blog.domain.member.service;

import com.jj.hello_blog.domain.member.entity.Member;
import com.jj.hello_blog.domain.member.repository.MemberRepository;
import com.jj.hello_blog.web.member.form.MemberForm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    Member signUp(MemberForm memberForm) {
        return memberRepository.signUp(memberForm.toMember());
    }
}
