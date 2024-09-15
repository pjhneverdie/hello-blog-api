package com.jj.hello_blog.domain.member.service;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.member.dto.MemberSignInDto;

import com.jj.hello_blog.domain.common.exception.CustomException;

import com.jj.hello_blog.domain.member.repository.MemberRepository;
import com.jj.hello_blog.domain.member.exception.MemberExceptionCode;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * signIn, 로그인
     */
    public Member signIn(MemberSignInDto memberSignInDto) {
        Optional<Member> member = memberRepository.selectMemberByEmail(memberSignInDto.getEmail());

        if (member.isPresent() && member.get().getPassword().equals(memberSignInDto.getPassword())) {
            return member.get();
        }

        throw new CustomException(MemberExceptionCode.SIGN_IN_FAILED);
    }

}
