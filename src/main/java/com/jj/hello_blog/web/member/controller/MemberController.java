package com.jj.hello_blog.web.member.controller;

import com.jj.hello_blog.domain.member.entity.Member;
import com.jj.hello_blog.domain.member.service.MemberService;
import com.jj.hello_blog.web.member.form.model.MemberForm;
import com.jj.hello_blog.web.member.form.group.save.MemberSaveGroupSequence;
import com.jj.hello_blog.web.member.form.model.SignInForm;
import com.jj.hello_blog.web.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signIn")
    public Member signIn(@Valid @RequestBody SignInForm signInForm, HttpSession session) {
        Member member = memberService.signIn(signInForm);

        if (member == null) {
            return null;
        }

        session.setAttribute(SessionConst.MEMBER_KEY, member);
        return member;
    }

    @PostMapping("/signUp")
    public Member signUp(@Validated(MemberSaveGroupSequence.class) @RequestBody MemberForm memberForm, HttpSession session) {
        Member member = memberService.signUp(memberForm);
        session.setAttribute(SessionConst.MEMBER_KEY, member);
        return member;
    }

    @GetMapping("/signUp/{email}")
    public boolean checkDuplicatedEmail(@Valid @NotNull @Email @PathVariable String email) {
        return memberService.checkDuplicatedEmail(email);
    }
}
