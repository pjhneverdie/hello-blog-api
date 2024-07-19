package com.jj.hello_blog.domain.member.service;

import com.jj.hello_blog.domain.member.entity.Member;
import com.jj.hello_blog.domain.member.repository.MemberRepository;
import com.jj.hello_blog.web.member.form.MemberSignInForm;
import com.jj.hello_blog.web.member.form.MemberSignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signIn(MemberSignInForm signInForm) {
        Optional<Member> member = memberRepository.findByEmail(signInForm.getEmail());

        if (member.isPresent() && member.get().getPassword().equals(signInForm.getPassword())) {
            return member.get();
        }

        return null;
    }

    public Member signUp(MemberSignUpForm memberSignUpForm) {
        Member member = new Member(null, memberSignUpForm.getEmail(), memberSignUpForm.getPassword());
        return memberRepository.signUp(member);
    }

    public boolean checkDuplicatedEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }
}
