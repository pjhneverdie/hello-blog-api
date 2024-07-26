package com.jj.hello_blog.web.member.controller;

import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.member.dto.MemberSignInDto;
import com.jj.hello_blog.domain.member.dto.MemberSignUpDto;
import com.jj.hello_blog.domain.member.service.MemberService;
import com.jj.hello_blog.domain.member.dto.MemberResponse;
import com.jj.hello_blog.web.member.form.MemberSignInForm;
import com.jj.hello_blog.web.member.form.MemberSignUpForm;
import com.jj.hello_blog.web.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public MemberResponse me(HttpSession session) {
        if (session == null) {
            return null;
        }

        Member member = (Member) session.getAttribute(SessionConst.MEMBER_KEY);

        return member == null ? null : new MemberResponse(member.getEmail());
    }

    @PostMapping("/signIn")
    public MemberResponse signIn(@Valid @RequestBody MemberSignInForm memberSignInForm, HttpSession session) {
        Optional<Member> member = memberService.signIn(new MemberSignInDto(memberSignInForm.getEmail(), memberSignInForm.getPassword()));

        if (member.isEmpty()) {
            return null;
        }

        session.setAttribute(SessionConst.MEMBER_KEY, member.get());

        return new MemberResponse(member.get().getEmail());
    }

    @PostMapping("/signUp")
    public MemberResponse signUp(@Valid @RequestBody MemberSignUpForm memberSignUpForm, HttpSession session) {
        Member member = memberService.signUp(new MemberSignUpDto(memberSignUpForm.getEmail(), memberSignUpForm.getPassword()));

        session.setAttribute(SessionConst.MEMBER_KEY, member);

        return new MemberResponse(member.getEmail());
    }

    @GetMapping("/signOut")
    public void signOut(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

    @GetMapping("/{email}")
    public boolean checkDuplicatedEmail(@Valid @NotNull @Email @PathVariable String email) {
        return memberService.checkDuplicatedEmail(email);
    }

}
