package com.jj.hello_blog.domain.member.service;

import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.member.dto.MemberSignInDto;
import com.jj.hello_blog.domain.member.dto.MemberSignUpDto;
import com.jj.hello_blog.domain.member.exception.MemberExceptionCode;
import com.jj.hello_blog.domain.member.repository.MemberRepository;
import com.jj.hello_blog.domain.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    /**
     * signUp, 회원가입
     */
    public Member signUp(MemberSignUpDto memberSignUpDto) {
        try {
            Member member = new Member(null, memberSignUpDto.getEmail(), memberSignUpDto.getPassword());

            memberRepository.insertMember(member);

            return member;
        } catch (DuplicateKeyException e) {
            // 이메일 중복 시
            throw new CustomException(MemberExceptionCode.DUPLICATED_EMAIL);
        }
    }

    /**
     * signOut, 회원탈퇴
     */
    public boolean deleteMember(int id) {
        memberRepository.deleteMemberById(id);
        return true;
    }

}
