package com.jj.hello_blog.domain.member.service;

import com.jj.hello_blog.domain.member.entity.Member;
import com.jj.hello_blog.domain.member.repository.MemberRepository;
import com.jj.hello_blog.web.member.form.model.MemberForm;
import com.jj.hello_blog.web.member.form.model.SignInForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member signIn(SignInForm signInForm) {
        Optional<Member> member = memberRepository.findByEmail(signInForm.getEmail());

        if (member.isPresent() && member.get().getPassword().equals(signInForm.getPassword())) {
            return member.get();
        }

        return null;
    }

    public Member signUp(MemberForm memberForm) {
        return memberRepository.signUp(memberForm.toMember());
    }

    public boolean checkDuplicatedEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }
}
