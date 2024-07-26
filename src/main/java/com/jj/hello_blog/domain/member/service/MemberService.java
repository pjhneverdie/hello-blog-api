package com.jj.hello_blog.domain.member.service;

import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.member.dto.MemberSignInDto;
import com.jj.hello_blog.domain.member.dto.MemberSignUpDto;
import com.jj.hello_blog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * signIn, 로그인
     *
     * @return 멤버
     */
    public Optional<Member> signIn(MemberSignInDto memberSignInDto) {
        Optional<Member> member = memberRepository.findMemberByEmail(memberSignInDto.getEmail());

        if (member.isPresent() && member.get().getPassword().equals(memberSignInDto.getPassword())) {
            return member;
        }

        return Optional.empty();
    }

    /**
     * signUp, 회원가입
     *
     * @return 멤버
     */
    public Member signUp(MemberSignUpDto memberSignUpDto) {
        Member member = new Member(null, memberSignUpDto.getEmail(), memberSignUpDto.getPassword());

        memberRepository.signUp(member);

        return member;
    }

    /**
     * checkDuplicatedEmail, 회원가입 전 중복 이메일 확인
     *
     * @return 이메일 중복 여부
     */
    public boolean checkDuplicatedEmail(String email) {
        return memberRepository.findMemberByEmail(email).isPresent();
    }

}
