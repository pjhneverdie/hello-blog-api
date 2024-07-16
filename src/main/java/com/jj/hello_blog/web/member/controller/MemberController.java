package com.jj.hello_blog.web.member.controller;

import com.jj.hello_blog.domain.member.entity.Member;
import com.jj.hello_blog.domain.member.service.MemberService;
import com.jj.hello_blog.web.member.dto.MemberResponse;
import com.jj.hello_blog.web.member.form.SignInForm;
import com.jj.hello_blog.web.member.form.SignUpForm;
import com.jj.hello_blog.web.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public MemberResponse signIn(@Valid @RequestBody SignInForm signInForm, HttpSession session) {
        Member member = memberService.signIn(signInForm);

        if (member == null) {
            return null;
        }

        session.setAttribute(SessionConst.MEMBER_KEY, member);

        return new MemberResponse(member.getEmail());
    }

    @PostMapping("/signUp")
    public MemberResponse signUp(@Valid @RequestBody SignUpForm signUpForm, HttpSession session) {
        Member member = memberService.signUp(signUpForm);

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
